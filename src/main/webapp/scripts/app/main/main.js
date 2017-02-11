'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('home', {
                parent: 'site',
                url: '/',
                data: {
                    authorities: []
                },
                /*Changed by amanur rahman according to Tumpa*/
                // views: {
                //     'content@': {
                //         templateUrl: 'scripts/app/main/main.html',
                //         controller: 'MainController'
                //     }
                // },

                views: {
                    'content@': {
                        templateUrl: 'scripts/app/account/login/login.html',
                        controller: 'LoginController'
                    }
                },
                /*Changed by amanur rahman according to Tumpa*/
                /*resolve: {
                    mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('main');
                        $translatePartialLoader.addPart('job');
                        $translatePartialLoader.addPart('jobapplication');
                        $translatePartialLoader.addPart('jobApplicationStatus');
                        return $translate.refresh();
                    }]
                }*/
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('login');
                        return $translate.refresh();
                    }]
                }

            })
            .state('home.about', {
                parent: 'home',
                url: '/about',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/main/about.html',
                        controller: 'MainController'
                    }
                },
                resolve: {
                    mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('main');
                        $translatePartialLoader.addPart('job');
                        $translatePartialLoader.addPart('jobapplication');
                        $translatePartialLoader.addPart('jobApplicationStatus');
                        return $translate.refresh();
                    }]
                }
            })
            .state('home.contact', {
                parent: 'home',
                url: '/contact',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/main/contactus.html',
                        controller: 'MainController'
                    }
                },
                resolve: {
                    mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('main');
                        $translatePartialLoader.addPart('job');
                        $translatePartialLoader.addPart('jobapplication');
                        $translatePartialLoader.addPart('jobApplicationStatus');
                        return $translate.refresh();
                    }]
                }
            })

         });
