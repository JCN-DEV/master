'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('trainingInfo.trainingRequisitionApproveAndForward', {
                parent: 'trainingInfo',
                url: '/trainingRequisitionApproveAndForwards',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.trainingRequisitionApproveAndForward.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/TrainingInformationSystem/trainingRequisitionApproveAndForward/trainingRequisitionApproveAndForwards.html',
                        controller: 'TrainingRequisitionApproveAndForwardController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('trainingRequisitionApproveAndForward');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('trainingInfo.trainingRequisitionApproveAndForward.detail', {
                parent: 'trainingInfo.trainingRequisitionApproveAndForward',
                url: '/trainingRequisitionApproveAndForward/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.trainingRequisitionApproveAndForward.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/TrainingInformationSystem/trainingRequisitionApproveAndForward/trainingRequisitionApproveAndForward-detail.html',
                        controller: 'TrainingRequisitionApproveAndForwardDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('trainingRequisitionApproveAndForward');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'TrainingRequisitionApproveAndForward', function($stateParams, TrainingRequisitionApproveAndForward) {
                        return TrainingRequisitionApproveAndForward.get({id : $stateParams.id});
                    }]
                }
            })
            //.state('trainingInfo.trainingRequisitionApproveAndForward.new', {
            //    parent: 'trainingInfo.trainingRequisitionApproveAndForward',
            //    url: '/new',
            //    data: {
            //        authorities: ['ROLE_USER'],
            //    },
            //    onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
            //        $modal.open({
            //            templateUrl: 'scripts/app/entities/TrainingInformationSystem/trainingRequisitionApproveAndForward/trainingRequisitionApproveAndForward-dialog.html',
            //            controller: 'TrainingRequisitionApproveAndForwardDialogController',
            //            size: 'lg',
            //            resolve: {
            //                entity: function () {
            //                    return {
            //                        approveStatus: null,
            //                        approveByAuthority: null,
            //                        forwardToAuthority: null,
            //                        logComments: null,
            //                        status: null,
            //                        createDate: null,
            //                        createBy: null,
            //                        updateDate: null,
            //                        updateBy: null,
            //                        id: null
            //                    };
            //                }
            //            }
            //        }).result.then(function(result) {
            //            $state.go('trainingRequisitionApproveAndForward', null, { reload: true });
            //        }, function() {
            //            $state.go('trainingRequisitionApproveAndForward');
            //        })
            //    }]
            //})
            .state('trainingInfo.trainingRequisitionApproveAndForward.edit', {
                parent: 'trainingInfo.trainingRequisitionApproveAndForward',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/TrainingInformationSystem/trainingRequisitionApproveAndForward/trainingRequisitionApproveAndForward-dialog.html',
                        controller: 'TrainingRequisitionApproveAndForwardDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['TrainingRequisitionApproveAndForward', function(TrainingRequisitionApproveAndForward) {
                                return TrainingRequisitionApproveAndForward.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('trainingRequisitionApproveAndForward', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
               // Approve the training requisition
            .state('trainingInfo.approve', {
                parent: 'trainingInfo',
                url: '/trainingRequisitionApproveAndForward/{trainingReqId}',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_AD','ROLE_DG'],
                    pageTitle: 'stepApp.trainingRequisitionApproveAndForward.home.title'
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/TrainingInformationSystem/trainingRequisitionApproveAndForward/trainingRequisitionApproveAndForward-dialog.html',
                        controller: 'TrainingRequisitionApproveAndForwardDialogController',
                        size: 'lg',
                        resolve: {
                            translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                                $translatePartialLoader.addPart('trainingRequisitionApproveAndForward');
                                $translatePartialLoader.addPart('global');
                                return $translate.refresh();
                            }],
                            entity: ['TrainingRequisitionApproveAndForward', function(TrainingRequisitionApproveAndForward) {
                                //  return EmployeeLoanApproveAndForward.get({id : $stateParams.id});
                                return {
                                    comments: null,
                                    approveStatus: null,
                                    createDate: null,
                                    createBy: null,
                                    updateDate: null,
                                    updateBy: null,
                                    id: null
                                };
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('trainingInfo.reqPendingList', null, { reload: true });
                    }, function() {
                        $state.go('trainingInfo.reqPendingList');
                    })
                }]
            })
            // Decline
            .state('trainingInfo.decline', {
                parent: 'trainingInfo',
                url: '/{trainingReqId}/requisition-Decline',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_AD','ROLE_DG'],
                    pageTitle: 'stepApp.trainingRequisitionApproveAndForward.home.title'
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/TrainingInformationSystem/trainingRequisitionApproveAndForward/trainingRequisition-decline.html',
                        controller: 'TrainingRequisitionDeclineController',
                        size: 'md',
                        resolve: {
                            translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                                $translatePartialLoader.addPart('trainingRequisitionApproveAndForward');
                                $translatePartialLoader.addPart('global');
                                return $translate.refresh();
                            }],
                            entity: ['TrainingRequisitionApproveAndForward', function(TrainingRequisitionApproveAndForward) {
                                //  return EmployeeLoanApproveAndForward.get({id : $stateParams.id});
                                return {
                                    comments: null,
                                    approveStatus: null,
                                    createDate: null,
                                    createBy: null,
                                    updateDate: null,
                                    updateBy: null,
                                    id: null
                                };
                            }]

                        }
                    }).result.then(function(result) {
                        $state.go('trainingInfo.reqPendingList', null, { reload: true });
                    }, function() {
                        $state.go('trainingInfo.reqPendingList');
                    })
                }]
            })
        // Reject
            .state('trainingInfo.reject', {
                parent: 'trainingInfo',
                url: '/{trainingReqId}/requisition-Reject/{reqID}',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_AD','ROLE_DG'],
                    pageTitle: 'stepApp.trainingRequisitionApproveAndForward.home.title'
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/TrainingInformationSystem/trainingRequisitionApproveAndForward/trainingRequisition-reject.html',
                        controller: 'TrainingRequisitionRejectController',
                        size: 'md',
                        resolve: {
                            translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                                $translatePartialLoader.addPart('trainingRequisitionApproveAndForward');
                                $translatePartialLoader.addPart('global');
                                return $translate.refresh();
                            }],
                            entity: ['TrainingRequisitionApproveAndForward', function(TrainingRequisitionApproveAndForward) {
                                //  return EmployeeLoanApproveAndForward.get({id : $stateParams.id});
                                return {
                                    comments: null,
                                    approveStatus: null,
                                    createDate: null,
                                    createBy: null,
                                    updateDate: null,
                                    updateBy: null,
                                    id: null
                                };
                            }]

                        }
                    }).result.then(function(result) {
                        $state.go('trainingInfo.reqPendingList', null, { reload: true });
                    }, function() {
                        $state.go('trainingInfo.reqPendingList');
                    })
                }]
            })
            // Get Approved List
            .state('trainingInfo.trainingApprovedList', {
                parent: 'trainingInfo',
                url: '/approvedList/{status}',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_AD','ROLE_DG'],
                    pageTitle: 'stepApp.trainingRequisitionApproveAndForward.home.title'
                },
                views: {
                    'trainingInfoView@trainingInfo': {
                        templateUrl: 'scripts/app/entities/TrainingInformationSystem/trainingRequisitionApproveAndForward/trainingRequisitionApprovedList.html',
                        controller: 'TrainingRequisitionApprovedListController'
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
            // Get Declined List
            .state('trainingInfo.trainingDeclinedList', {
                parent: 'trainingInfo',
                url: '/declinedList/{status}',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_AD','ROLE_DG'],
                    pageTitle: 'stepApp.trainingRequisitionApproveAndForward.home.title'
                },
                views: {
                    'trainingInfoView@trainingInfo': {
                        templateUrl: 'scripts/app/entities/TrainingInformationSystem/trainingRequisitionApproveAndForward/trainingRequisitionApprovedList.html',
                        controller: 'TrainingRequisitionApprovedListController'
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

            // Get Rejected List
            .state('trainingInfo.trainingRejectedList', {
                parent: 'trainingInfo',
                url: '/rejectedList/{status}',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_AD','ROLE_DG'],
                    pageTitle: 'stepApp.trainingRequisitionApproveAndForward.home.title'
                },
                views: {
                    'trainingInfoView@trainingInfo': {
                        templateUrl: 'scripts/app/entities/TrainingInformationSystem/trainingRequisitionApproveAndForward/trainingRequisitionApprovedList.html',
                        controller: 'TrainingRequisitionApprovedListController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('trainingRequisitionForm');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            });
    });
