'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('employeeLoanInfo.approve', {
                parent: 'employeeLoanInfo',
                url: '/employeeLoanApproveAndForward/{loanReqId}',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_INSTITUTE','ROLE_AD','ROLE_DG','ROLE_DIRECTOR','ROLE_DIRECTOR_VOC','ROLE_ADVOC','ROLE_MINISTRY'],
                    pageTitle: 'stepApp.employeeLoanApproveAndForward.home.title'
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/ELMS/employeeLoanApproveAndForward/employeeLoanApproveAndForward-dialog.html',
                        controller: 'EmployeeLoanApproveAndForwardDialogController',
                        size: 'lg',
                        resolve: {
                            translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                                $translatePartialLoader.addPart('employeeLoanApproveAndForward');
                                $translatePartialLoader.addPart('global');
                                return $translate.refresh();
                            }],
                            entity: ['EmployeeLoanApproveAndForward', function(EmployeeLoanApproveAndForward) {
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
                        $state.go('employeeLoanInfo.employeeLoanReqPending', null, { reload: true });
                    }, function() {
                        $state.go('employeeLoanInfo.employeeLoanReqPending');
                    })
                }]
            })

         .state('employeeLoanInfo.decline', {
            parent: 'employeeLoanInfo',
            url: '/{loanReqId}/employeeLoan-Decline',
            data: {
                authorities: ['ROLE_ADMIN','ROLE_INSTITUTE','ROLE_AD','ROLE_DG','ROLE_DIRECTOR'],
                pageTitle: 'stepApp.employeeLoanApproveAndForward.home.title'
            },
            onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                $modal.open({
                    templateUrl: 'scripts/app/entities/ELMS/employeeLoanApproveAndForward/employeeLoan-decline.html',
                    controller: 'EmployeeLoanDeclineController',
                    size: 'md',
                    resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('employeeLoanApproveAndForward');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: ['EmployeeLoanApproveAndForward', function(EmployeeLoanApproveAndForward) {
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
                    $state.go('employeeLoanInfo.employeeLoanReqPending', null, { reload: true });
                }, function() {
                    $state.go('employeeLoanInfo.employeeLoanReqPending');
                })
            }]
         })
//            .state('employeeLoanApproveAndForward.detail', {
//                parent: 'entity',
//                url: '/employeeLoanApproveAndForward/{id}',
//                data: {
//                    authorities: ['ROLE_ADMIN'],
//                    pageTitle: 'stepApp.employeeLoanApproveAndForward.detail.title'
//                },
//                views: {
//                    'content@': {
//                        templateUrl: 'scripts/app/entities/ELMS/employeeLoanApproveAndForward/employeeLoanApproveAndForward-detail.html',
//                        controller: 'EmployeeLoanApproveAndForwardDetailController'
//                    }
//                },
//                resolve: {
//                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
//                        $translatePartialLoader.addPart('employeeLoanApproveAndForward');
//                        return $translate.refresh();
//                    }],
//                    entity: ['$stateParams', 'EmployeeLoanApproveAndForward', function($stateParams, EmployeeLoanApproveAndForward) {
//                        return EmployeeLoanApproveAndForward.get({id : $stateParams.id});
//                    }]
//                }
//            })
//            .state('employeeLoanApproveAndForward.new', {
//                parent: 'employeeLoanApproveAndForward',
//                url: '/new',
//                data: {
//                    authorities: ['ROLE_USER'],
//                },
//                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
//                    $modal.open({
//                        templateUrl: 'scripts/app/entities/employeeLoanApproveAndForward/employeeLoanApproveAndForward-dialog.html',
//                        controller: 'EmployeeLoanApproveAndForwardDialogController',
//                        size: 'lg',
//                        resolve: {
//                            entity: function () {
//                                return {
//                                    comments: null,
//                                    approveStatus: null,
//                                    createDate: null,
//                                    createBy: null,
//                                    updateDate: null,
//                                    updateBy: null,
//                                    id: null
//                                };
//                            }
//                        }
//                    }).result.then(function(result) {
//                        $state.go('employeeLoanApproveAndForward', null, { reload: true });
//                    }, function() {
//                        $state.go('employeeLoanApproveAndForward');
//                    })
//                }]
//            })
//            .state('employeeLoanApproveAndForward.edit', {
//                parent: 'employeeLoanApproveAndForward',
//                url: '/{id}/edit',
//                data: {
//                    authorities: ['ROLE_USER'],
//                },
//                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
//                    $modal.open({
//                        templateUrl: 'scripts/app/entities/employeeLoanApproveAndForward/employeeLoanApproveAndForward-dialog.html',
//                        controller: 'EmployeeLoanApproveAndForwardDialogController',
//                        size: 'lg',
//                        resolve: {
//                            entity: ['EmployeeLoanApproveAndForward', function(EmployeeLoanApproveAndForward) {
//                                return EmployeeLoanApproveAndForward.get({id : $stateParams.id});
//                            }]
//                        }
//                    }).result.then(function(result) {
//                        $state.go('employeeLoanApproveAndForward', null, { reload: true });
//                    }, function() {
//                        $state.go('^');
//                    })
//                }]
//            });
    });
