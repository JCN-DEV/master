'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('sis', {
                parent: 'entity',
                url: '/sis',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.sisHome.home.generalHomeTitle'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/sis/studentInfoSystem.html',
                        controller: 'StudentInfoSystemController'
                    },
                   'sisView@sis':{
                        templateUrl: 'scripts/app/entities/sis/dashboard.html',
                        controller: 'StudentInfoSystemController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('sisHome');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('leftmenu');
                        return $translate.refresh();
                    }]
                }
            })
            .state('sis.dlContUpld', {
                            parent: 'sis',
                            url: '/library',
                            data: {
                                authorities: [],
                                pageTitle: 'stepApp.dlContUpld.home.title'
                            },
                            views: {
                               'sisView@sis': {
                                    templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlContUpld/dlContUplds.html',
                                    controller: 'DlContUpldController'
                                }
                            },
                            resolve: {
                                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                                    $translatePartialLoader.addPart('dlContUpld');
                                    $translatePartialLoader.addPart('global');
                                    return $translate.refresh();
                                }]
                            }
                        })
    });
