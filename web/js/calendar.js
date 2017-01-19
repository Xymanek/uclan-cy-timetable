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

    Start: null,

    Init: function () {
        TimeScheduler.Options.GetSections = Calendar.GetSections;
        TimeScheduler.Options.GetSchedule = Calendar.GetSchedule;
        TimeScheduler.Options.Start = Calendar.start;
        TimeScheduler.Options.Periods = Calendar.Periods;
        TimeScheduler.Options.SelectedPeriod = '1 day';
        TimeScheduler.Options.Element = $('.calendar');

        TimeScheduler.Options.ShowCurrentTime = true;
        TimeScheduler.Options.ShowGoto = false;
        TimeScheduler.Options.ShowToday = false;

        TimeScheduler.Options.Text.NextButton = '&nbsp;';
        TimeScheduler.Options.Text.PrevButton = '&nbsp;';

        TimeScheduler.Options.MaxHeight = 10;

        TimeScheduler.Init();

        $('.time-sch-content-header-wrap').stick_in_parent();

        if ($("body").height() > $(window).height()) {
            setInterval(function () {
                Calendar.VerticalScroll();
            }, 40000);
        }

        // Live clock
        var clockContainer = $('.time-sch-times-header-0 td:first-child');

        clockContainer.attr('rowspan', '2');
        $('.time-sch-times-header-1 td:first-child').remove();

        this.updateClock(clockContainer);
        setInterval(this.updateClock, 500, clockContainer);
    },

    GetSections: function (callback) {
        callback(Calendar.Sections);
    },

    GetSchedule: function (callback) {
        callback(Calendar.Items);
    },

    VerticalScroll: function () {
        $("html, body")
            .animate({scrollTop: $(document).height() - $(window).height()}, this.scrollTime)
            .delay(this.delayTime)
            .animate({scrollTop: 0}, this.scrollTime);
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
    var url = 'data.json';

    if (typeof day === 'string') {
        url += '?day=' + day;
    }

    $
        .getJSON(url, function (data) {
            Calendar.start = moment(data.startAt, 'DD/MM/YYYY HH:mm:ss');
            Calendar.Sections = data.rooms;

            data.sessions.forEach(function (session) {
                Calendar.Items.push({
                    id: session.id,
                    name: '<div>' + session.code + ': ' + session.name + '</div><div>' + session.description + ' with ' + session.lecturer + '</div>',
                    sectionID: session.room.id,
                    start: moment(session.start, 'DD/MM/YYYY HH:mm:ss'),
                    end: moment(session.end, 'DD/MM/YYYY HH:mm:ss')
                });
            });

            Calendar.Init();
        })
        .fail(function () {
            $('.calendar').html('<span style="color: red;">Something went wrong :(</span>');
        });
});