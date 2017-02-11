'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('design', {
                parent: 'account',
                url: '/vacancy',
                data: {
                    authorities: ['ROLE_ADMIN', 'ROLE_EMPLOYER', 'ROLE_MANAGER', 'ROLE_USER'],
                    pageTitle: 'global.menu.account.resume'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/account/designs/vacancy.html'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('educationalQualification');
                        $translatePartialLoader.addPart('professionalQualification');
                        $translatePartialLoader.addPart('experience');
                        $translatePartialLoader.addPart('settings');
                        $translatePartialLoader.addPart('resume');
                        $translatePartialLoader.addPart('employee');
                        $translatePartialLoader.addPart('skill');
                        $translatePartialLoader.addPart('training');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('lang');
                        $translatePartialLoader.addPart('reference');
                        return $translate.refresh();

                    }]
                }
            })

    });
