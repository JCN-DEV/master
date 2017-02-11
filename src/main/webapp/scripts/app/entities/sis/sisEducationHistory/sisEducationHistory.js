'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('sisEducationHistory', {
                parent: 'sis',
                url: '/sisEducationHistorys',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.sisEducationHistory.home.title'
                },
                views: {
                    'sisView@sis': {
                        templateUrl: 'scripts/app/entities/sis/sisEducationHistory/sisEducationHistorys.html',
                        controller: 'SisEducationHistoryController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('sisEducationHistory');
                        $translatePartialLoader.addPart('achievedCertificate');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('sisEducationHistory.detail', {
                parent: 'sisEducationHistory',
                url: '/sisEducationHistory/{id}',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.sisEducationHistory.detail.title'
                },
                views: {
                    'sisView@sis': {
                        templateUrl: 'scripts/app/entities/sis/sisEducationHistory/sisEducationHistory-detail.html',
                        controller: 'SisEducationHistoryDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('sisEducationHistory');
                        //$translatePartialLoader.addPart('achievedCertificate');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'SisEducationHistory', function($stateParams, SisEducationHistory) {
                        return SisEducationHistory.get({id : $stateParams.id});
                    }]
                }
            })
            .state('sisEducationHistory.new', {
                parent: 'sisEducationHistory',
                url: '/new',
                data: {
                    authorities: [],
                },
                views: {
                    'sisView@sis': {
                        templateUrl: 'scripts/app/entities/sis/sisEducationHistory/sisEducationHistory-dialog.html',
                        controller: 'SisEducationHistoryDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('sisEducationHistory');
                        //$translatePartialLoader.addPart('achievedCertificate');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            yearOrSemester: null,
                            rollNo: null,
                            majorOrDept: null,
                            divisionOrGpa: null,
                            passingYear: null,
                            achievedCertificate: null,
                            activeStatus: true
                        };
                    }
                }
            })
            .state('sisEducationHistory.edit', {
                parent: 'sisEducationHistory',
                url: '/{id}/edit',
                data: {
                    authorities: []
                },
                views: {
                    'sisView@sis': {
                        templateUrl: 'scripts/app/entities/sis/sisEducationHistory/sisEducationHistory-dialog.html',
                        controller: 'SisEducationHistoryDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('sisEducationHistory');
                        //$translatePartialLoader.addPart('achievedCertificate');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'SisEducationHistory', function($stateParams, SisEducationHistory) {
                        return SisEducationHistory.get({id : $stateParams.id});
                    }]
                }
            });
    });
