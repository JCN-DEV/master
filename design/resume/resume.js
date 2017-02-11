'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('resume', {
                parent: 'account',
                url: '/resume',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'global.menu.account.resume'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/account/resume/resume.html',
                        controller: 'ResumeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('educationalQualification');
                        $translatePartialLoader.addPart('settings');
                        $translatePartialLoader.addPart('resume');
                        return $translate.refresh();
                    }]
                }
            })

            .state('resume.view', {
                parent: 'account',
                url: '/view-resume',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'global.menu.account.viewResume'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/account/resume/view-resume.html',
                        controller: 'ViewResumeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('settings');
                        $translatePartialLoader.addPart('sessions');
                        $translatePartialLoader.addPart('resume');
                        return $translate.refresh();
                    }]
                }
            })
            .state('resume.change-password', {
                parent: 'account',
                url: '/change-password',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'global.menu.account.change-password'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/account/resume/change-password.html',
                        controller: 'ChangePasswordController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('settings');
                        $translatePartialLoader.addPart('sessions');
                        $translatePartialLoader.addPart('resume');
                        return $translate.refresh();
                    }]
                }
            });
    });
