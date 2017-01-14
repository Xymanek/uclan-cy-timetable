var today = moment().startOf('day');

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

    Items: [
        {
            id: 20,
            name: '<div>Item 1</div><div>Sub Info</div>',
            sectionID: 1,
            start: moment(today).add('days', -1),
            end: moment(today).add('days', 3)
        },
        {
            id: 21,
            name: '<div>Item 2</div><div>Sub Info</div>',
            sectionID: 3,
            start: moment(today).add('days', -1),
            end: moment(today).add('days', 3)
        },
        {
            id: 22,
            name: '<div>Item 3</div>',
            start: moment(today).add('hours', 12),
            end: moment(today).add('days', 3).add('hours', 4),
            sectionID: 1
        }
    ],

    Sections: [
        {
            id: 1,
            name: 'Section 1'
        },
        {
            id: 2,
            name: 'Section 2'
        },
        {
            id: 3,
            name: 'Section 3'
        }
    ],

    Init: function () {
        TimeScheduler.Options.GetSections = Calendar.GetSections;
        TimeScheduler.Options.GetSchedule = Calendar.GetSchedule;
        TimeScheduler.Options.Start = moment().add('hours', -1).startOf('hour');
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
            console.log("Vertical Scrollbar! :D");

            this.VerticalScroll();
            setInterval(function () {
                Calendar.VerticalScroll();
            }, 40000);
        }
    },

    GetSections: function (callback) {
        callback(Calendar.Sections);
    },

    GetSchedule: function (callback) {
        callback(Calendar.Items);
    },

    VerticalScroll: function () {
        $("html, body")
            .animate({ scrollTop: $(document).height() - $(window).height() }, this.scrollTime)
            .delay(this.delayTime)
            .animate({ scrollTop: 0 }, this.scrollTime);
    }
};

$(document).ready(function () {
    var url = 'data.json';

    if (typeof day === 'string') {
        url += '?day=' + day;
    }

    $.getJSON(url, function (data) {
        Calendar.Sections = data.rooms;
        Calendar.Items = [];

        data.sessions.forEach(function (session) {
            Calendar.Items.push({
                id: session.id,
                name: '<div>' + session.code +': '+ session.name + '</div><div>' + session.description + ' with '+session.lecturer+'</div>',
                sectionID: session.room.id,
                start: moment(session.start, 'HH:mm'),
                end: moment(session.end, 'HH:mm')
            });
        });

        Calendar.Init();
    }).fail(function () {
        $('.calendar').html('<span style="color: red;">Something went wrong :(</span>');
    });
});