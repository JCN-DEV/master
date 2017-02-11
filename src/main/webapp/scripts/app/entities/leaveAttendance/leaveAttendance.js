'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('alm', {
                parent: 'entity',
                url: '/alm',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.hrmHome.home.generalHomeTitle'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/leaveAttendance/leaveAttendance.html',
                        controller: 'LeaveAttendanceController'
                    },
                   'leaveAttendanceView@alm':{
                        templateUrl: 'scripts/app/entities/leaveAttendance/dashboard.html',
                        controller: 'LeaveAttendanceController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('leaveAttendanceHome');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('leftmenu');
                        return $translate.refresh();
                    }]
                }
            })
    });
