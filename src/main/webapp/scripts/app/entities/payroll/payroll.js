'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('payroll', {
                parent: 'entity',
                url: '/payroll',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.prlHome.home.generalHomeTitle'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/payroll/payroll.html',
                        controller: 'payrollHomeController'
                    },
                    'payrollManagementView@payroll': {
                        templateUrl: 'scripts/app/entities/payroll/payroll-dashboard.html',
                        controller: 'payrollHomeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prlHome');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('leftmenu');
                        return $translate.refresh();
                    }]
                }
            })
    });
