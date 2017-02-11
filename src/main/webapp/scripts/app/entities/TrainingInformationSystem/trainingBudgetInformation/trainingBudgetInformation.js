'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('trainingInfo.trainingBudgetInformation', {
                parent: 'trainingInfo',
                url: '/trainingBudgetInformations',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.trainingBudgetInformation.home.title'
                },
                views: {
                    'trainingInfoView@trainingInfo': {
                        templateUrl: 'scripts/app/entities/TrainingInformationSystem/trainingBudgetInformation/trainingBudgetInformations.html',
                        controller: 'TrainingBudgetInformationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('trainingBudgetInformation');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('trainingInfo.trainingBudgetInformation.detail', {
                parent: 'trainingInfo.trainingBudgetInformation',
                url: '/trainingBudgetInformation/{id}',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.trainingBudgetInformation.detail.title'
                },
                views: {
                    'trainingInfoView@trainingInfo': {
                        templateUrl: 'scripts/app/entities/TrainingInformationSystem/trainingBudgetInformation/trainingBudgetInformation-detail.html',
                        controller: 'TrainingBudgetInformationDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('trainingBudgetInformation');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'TrainingBudgetInformation', function($stateParams, TrainingBudgetInformation) {
                        return TrainingBudgetInformation.get({id : $stateParams.id});
                    }]
                }
            })
            .state('trainingInfo.trainingBudgetInformation.new', {
                parent: 'trainingInfo.trainingBudgetInformation',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                views: {
                    'trainingInfoView@trainingInfo': {
                        templateUrl: 'scripts/app/entities/TrainingInformationSystem/trainingBudgetInformation/trainingBudgetInformation-dialog.html',
                        controller: 'TrainingBudgetInformationDialogController'
                    }
                },
                resolve: {
                    entity: function () {
                        return {
                            budgetAmount: null,
                            expenseAmount: null,
                            remarks: null,
                            status: null,
                            createDate: null,
                            createBy: null,
                            updateDate: null,
                            updateBy: null,
                            id: null
                        };
                    }
                }
            })
            .state('trainingInfo.trainingBudgetInformation.edit', {
                parent: 'trainingInfo.trainingBudgetInformation',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.trainingBudgetInformation.detail.title'
                },
                views: {
                    'trainingInfoView@trainingInfo': {
                        templateUrl: 'scripts/app/entities/TrainingInformationSystem/trainingBudgetInformation/trainingBudgetInformation-dialog.html',
                        controller: 'TrainingBudgetInformationDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('trainingBudgetInformation');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'TrainingBudgetInformation', function($stateParams, TrainingBudgetInformation) {
                        return TrainingBudgetInformation.get({id : $stateParams.id});
                    }]
                }
            });
    });
