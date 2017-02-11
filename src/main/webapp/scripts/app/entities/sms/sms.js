'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('sms', {
                parent: 'entity',
                url: '/sms',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instGenInfo.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/sms/sms.html',
                        controller: 'smsHomeController'
                    },
                   'smsManagementView@sms':{
                        templateUrl: 'scripts/app/entities/sms/sms-dashboard.html',
                        controller: 'smsHomeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('smsServiceComplaint');
                        $translatePartialLoader.addPart('priority');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
    });
