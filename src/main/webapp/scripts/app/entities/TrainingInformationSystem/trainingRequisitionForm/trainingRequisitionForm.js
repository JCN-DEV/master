'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('trainingInfo.trainingRequisitionForm', {
                parent: 'trainingInfo',
                url: '/trainingRequisitionForms',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_INSTITUTE','ROLE_USER','ROLE_AD','ROLE_DG','ROLE_TIS_DASH'],
                    pageTitle: 'stepApp.trainingRequisitionForm.home.title'
                },
                views: {
                    'trainingInfoView@trainingInfo': {
                        templateUrl: 'scripts/app/entities/TrainingInformationSystem/trainingRequisitionForm/trainingRequisitionForms.html',
                        controller: 'TrainingRequisitionFormController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('trainingRequisitionForm');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('trainingInfo.trainingRequisitionForm.detail', {
                parent: 'trainingInfo.trainingRequisitionForm',
                url: '/trainingRequisitionForm/{id}',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_INSTITUTE','ROLE_USER','ROLE_AD','ROLE_DG','ROLE_TIS_DASH'],
                    pageTitle: 'stepApp.trainingRequisitionForm.detail.title'
                },
                views: {
                    'trainingInfoView@trainingInfo': {
                        templateUrl: 'scripts/app/entities/TrainingInformationSystem/trainingRequisitionForm/trainingRequisitionForm-detail.html',
                        controller: 'TrainingRequisitionFormDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('trainingRequisitionForm');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'TrainingRequisitionForm', function($stateParams, TrainingRequisitionForm) {
                        return TrainingRequisitionForm.get({id : $stateParams.id});
                    }]
                }
            })
            .state('trainingInfo.trainingRequisitionForm.new', {
                parent: 'trainingInfo.trainingRequisitionForm',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_INSTITUTE','ROLE_USER','ROLE_AD','ROLE_DG','ROLE_TIS_DASH'],
                },
                views: {
                    'trainingInfoView@trainingInfo': {
                        templateUrl: 'scripts/app/entities/TrainingInformationSystem/trainingRequisitionForm/trainingRequisitionForm-dialog.html',
                        controller: 'TrainingRequisitionFormDialogController'
                    }
                },
                resolve: {
                    entity: function () {
                        return {
                            requisitionCode: null,
                            trainingType: null,
                            session: null,
                            applyDate: null,
                            reason: null,
                            fileName: null,
                            file: null,
                            fileContentType: null,
                            fileContentName: null,
                            applyBy: null,
                            instituteId: null,
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
            .state('trainingInfo.trainingRequisitionForm.edit', {
                parent: 'trainingInfo.trainingRequisitionForm',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_INSTITUTE','ROLE_USER','ROLE_TIS_DASH'],
                    pageTitle: 'stepApp.trainingRequisitionForm.detail.title'
                },
                views: {
                    'trainingInfoView@trainingInfo': {
                        templateUrl: 'scripts/app/entities/TrainingInformationSystem/trainingRequisitionForm/trainingRequisitionForm-dialog.html',
                        controller: 'TrainingRequisitionFormDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('trainingRequisitionForm');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'TrainingRequisitionForm', function($stateParams, TrainingRequisitionForm) {
                        return TrainingRequisitionForm.get({id : $stateParams.id});
                    }]
                }
            })

            .state('trainingInfo.reqPendingList', {
                parent: 'trainingInfo',
                url: '/trainingRequisitionForm/reqPending',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_AD','ROLE_DG','ROLE_TIS_DASH'],
                    pageTitle: 'stepApp.trainingRequisitionForm.home.title'
                },
                views: {
                    'trainingInfoView@trainingInfo': {
                        templateUrl: 'scripts/app/entities/TrainingInformationSystem/trainingRequisitionForm/trainingReq-Pending.html',
                        controller: 'TrainingReqPendingController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('trainingRequisitionForm');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })

            .state('trainingInfo.viewAndApprove', {
                parent: 'trainingInfo',
                url: '/viewAndApprove/{reqID}',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_AD','ROLE_DG','ROLE_TIS_DASH'],
                    pageTitle: 'stepApp.employeeLoanRequisitionForm.home.title'
                },
                views: {
                    'trainingInfoView@trainingInfo': {
                        templateUrl: 'scripts/app/entities/TrainingInformationSystem/trainingRequisitionForm/trainingRequisitionApprove-panel.html',
                        controller: 'TrainingRequisitionApprovePanelController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('trainingRequisitionForm');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'TrainingRequisitionForm', function($stateParams, TrainingRequisitionForm) {
                        return TrainingRequisitionForm.get({id : $stateParams.reqID});
                    }]
                }
            });
    });
