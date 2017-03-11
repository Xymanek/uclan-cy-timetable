var Calendar = {
    scrollTime: 10000, // 10 sec
    delayTime: 10000, // 10 sec

    Periods: [
        {
            Name: '1 day',
            Label: '1 day',
            TimeframePeriod: (30),
            TimeframeOverall: (60 * 4),
            TimeframeHeaders: [
                'Do MMM',
                'HH:mm'
            ],
            Classes: 'period-1day'
        }
    ],

    Items: [],
    Sections: [],
    clockInterval: null,
    scrollInterval: null,

    FetchAndPopulate: function (onSuccess, onFail, always) {
        var url = 'data.json';

        if (typeof day === 'string') {
            url += '?day=' + day;
        }

        var promise = $
            .getJSON(url, function (data) {
                Calendar.Sections = data.rooms;
                Calendar.Items = [];

                data.sessions.forEach(function (session) {
                    Calendar.Items.push({
                        id: session.id,
                        name: '<div>' + session.code + ': ' + session.name + '</div><div>' + session.description + ' with ' + session.lecturer + '</div>',
                        sectionID: session.room.id,
                        start: moment(session.start, 'DD/MM/YYYY HH:mm:ss'),
                        end: moment(session.end, 'DD/MM/YYYY HH:mm:ss')
                    });
                });

                onSuccess();
            })
            .fail(onFail);

        if (typeof always !== 'undefined') {
            promise.always(always);
        }
    },

    Init: function () {
        TimeScheduler.Options.GetSections = Calendar.GetSections;
        TimeScheduler.Options.GetSchedule = Calendar.GetSchedule;
        TimeScheduler.Options.Periods = Calendar.Periods;
        TimeScheduler.Options.SelectedPeriod = '1 day';
        TimeScheduler.Options.Element = $('.calendar');

        TimeScheduler.Options.ShowCurrentTime = true;
        TimeScheduler.Options.ShowGoto = false;
        TimeScheduler.Options.ShowToday = false;

        TimeScheduler.Options.Text.NextButton = '&nbsp;';
        TimeScheduler.Options.Text.PrevButton = '&nbsp;';

        var start = this.GetStart();

        TimeScheduler.Options.Start = start;
        TimeScheduler.Init();

        this.InitScroll();
        this.InitClock();

        // Queue up update
        setTimeout(
            Calendar.Update,
            Calendar.GetNextUpdate(start)
        );
    },

    Update: function () {
        // Remove old intervals
        clearInterval(this.clockInterval);
        clearInterval(this.scrollInterval);

        // Move the calendar
        TimeScheduler.Options.Start = Calendar.GetStart();
        TimeScheduler.Init();

        // Add a message
        $('.time-sch-content-header-wrap').prepend(
            '<div style="text-align: center" id="notice-msg">Refreshing data...</div>'
        );

        // Add scroll and clock
        Calendar.InitClock();
        Calendar.InitScroll();

        // Fetch new data
        Calendar.FetchAndPopulate(
            function () {
                // Remove old intervals
                clearInterval(Calendar.clockInterval);
                clearInterval(Calendar.scrollInterval);

                // Move the calendar
                TimeScheduler.Options.Start = Calendar.GetStart();
                TimeScheduler.Init(true);

                // Add scroll and clock
                Calendar.InitClock();
                Calendar.InitScroll();
            },
            function () {
                $('#notice-msg').html(
                    '<span style="color: red;">Something went wrong during refresh, displaying old data</span>'
                );
            },
            function () {
                // Schedule next update
                setTimeout(
                    Calendar.Update,
                    Calendar.GetNextUpdate(TimeScheduler.Options.Start)
                );
            }
        );
    },

    GetSelectedDateWithCurrentTime: function () {
        var today = new Date(),
            result = moment(day, 'DD/MM/YYYY');

        return result
            .hours(today.getHours())
            .minutes(today.getMinutes())
            .seconds(today.getSeconds());
    },

    GetStart: function () {
        var result = this.GetSelectedDateWithCurrentTime();

        result
            .seconds(0)
            .subtract(30, 'minutes')
            .minutes(result.minutes() > 30 ? 30 : 0);

        return result;
    },

    GetNextUpdate: function (start) {
        return start
            .clone()
            .add(1, 'hours')
            .diff(this.GetSelectedDateWithCurrentTime());
    },

    GetSections: function (callback) {
        callback(Calendar.Sections);
    },

    GetSchedule: function (callback) {
        callback(Calendar.Items);
    },

    InitScroll: function () {
        if ($("body").height() > $(window).height()) {
            $('.time-sch-content-header-wrap').stick_in_parent();

            this.scrollInterval = setInterval(function () {
                Calendar.VerticalScroll();
            }, 40000);
        }
    },

    VerticalScroll: function () {
        $("html, body")
            .animate({scrollTop: $(document).height() - $(window).height()}, this.scrollTime)
            .delay(this.delayTime)
            .animate({scrollTop: 0}, this.scrollTime);
    },

    InitClock: function () {
        var clockContainer = $('.time-sch-times-header-0 td:first-child');

        clockContainer.attr('rowspan', '2');
        $('.time-sch-times-header-1 td:first-child').remove();

        this.updateClock(clockContainer);
        this.clockInterval = setInterval(this.updateClock, 500, clockContainer);
    },

    updateClock: function (container) {
        var today = new Date(),
            h = Calendar.checkTime(today.getHours()),
            m = Calendar.checkTime(today.getMinutes()),
            s = Calendar.checkTime(today.getSeconds());

        container.html(h + ":" + m + ":" + s);
    },

    checkTime: function (i) {
        return (i < 10) ? "0" + i : i;
    }
};

$(document).ready(function () {
    Calendar.FetchAndPopulate(
        function () {
            Calendar.Init();
        },
        function () {
            $('.calendar').html('<span style="color: red;">Something went wrong :(</span>');
        }
    );
});