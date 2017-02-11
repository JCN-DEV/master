'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('pfms', {
                parent: 'entity',
                url: '/pfms',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.pfmsHome.home.generalHomeTitle',
                    displayName: 'PFMS Dashboard'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/pfms/providentFundms.html',
                        controller: 'ProvidentFundmsController'
                    },
                    'pfmsView@pfms': {
                        templateUrl: 'scripts/app/entities/pfms/dashboard.html',
                        controller: 'ProvidentFundmsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pfmsHome');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('leftmenu');
                        return $translate.refresh();
                    }]
                }
            })
            .state('pfms.dashboardMenu', {
            parent: 'pfms',
            url: '/dashboardMenu',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'stepApp.pfmsHome.home.generalHomeTitle',
                displayName: 'PFMS Menus '
            },
            views: {
                'pfmsView@pfms': {
                    templateUrl: 'scripts/app/entities/pfms/dashboardMenu.html',
                    controller: 'ProvidentFundmsController'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pfmsHome');
                    $translatePartialLoader.addPart('global');
                    $translatePartialLoader.addPart('leftmenu');
                    return $translate.refresh();
                }]
            }
        })
    });
