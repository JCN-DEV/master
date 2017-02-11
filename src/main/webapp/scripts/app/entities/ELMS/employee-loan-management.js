'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('employeeLoanInfo', {
                parent: 'entity',
                url: '/employee-loan-info',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.employeeLoanInfo.home.title'
                },

                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/ELMS/employee-loan-info.html',
                        controller: 'EmployeeLoanInfoController'
                    },
                    'employeeLoanView@employeeLoanInfo':{
                         templateUrl: 'scripts/app/entities/ELMS/employee-loan-dashboard.html',
                         controller: 'EmployeeLoanInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                       return $translate.refresh();
                    }]
                }
            })
            .state('employeeLoanInfo.dashboard', {
                parent: 'employeeLoanInfo',
                url: '/employee-loan-dashboard',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.employeeLoanInfo.home.title'
                },
                views: {
                    'employeeLoanView@employeeLoanInfo':{
                         templateUrl: 'scripts/app/entities/ELMS/employee-loan-dashboard.html',
                         controller: 'EmployeeLoanInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {

                        return $translate.refresh();
                    }]
                }
            })

            // For Voc Application
            .state('employeeLoanInfo.loanReqPendingFromVoc', {
                parent: 'employeeLoanInfo',
                url: '/loanApplicationPending/{applyType}',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_INSTITUTE','ROLE_AD','ROLE_DG','ROLE_DIRECTOR','ROLE_DIRECTOR_VOC','ROLE_ADVOC','ROLE_MINISTRY'],
                    pageTitle: 'stepApp.employeeLoanRequisitionForm.home.title'
                },
                views: {
                    'employeeLoanView@employeeLoanInfo': {
                        templateUrl: 'scripts/app/entities/ELMS/employeeLoanApplicationPending.html',
                        controller: 'EmployeeLoanApplicationPendingController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('employeeLoanRequisitionForm');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })

            // For Poly & Others
            .state('employeeLoanInfo.loanReqPendingFromOthers', {
                parent: 'employeeLoanInfo',
                url: '/loanApplicationPending/{applyType}',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_INSTITUTE','ROLE_AD','ROLE_DG','ROLE_DIRECTOR','ROLE_DIRECTOR_VOC','ROLE_ADVOC','ROLE_MINISTRY'],
                    pageTitle: 'stepApp.employeeLoanRequisitionForm.home.title'
                },
                views: {
                    'employeeLoanView@employeeLoanInfo': {
                        templateUrl: 'scripts/app/entities/ELMS/employeeLoanApplicationPending.html',
                        controller: 'EmployeeLoanApplicationPendingController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('employeeLoanRequisitionForm');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            // DTE Employee
            .state('employeeLoanInfo.loanReqPendingFromDTE', {
                parent: 'employeeLoanInfo',
                url: '/loanApplicationPending/{applyType}',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_INSTITUTE','ROLE_AD','ROLE_DG','ROLE_DIRECTOR','ROLE_DIRECTOR_VOC','ROLE_ADVOC','ROLE_MINISTRY'],
                    pageTitle: 'stepApp.employeeLoanRequisitionForm.home.title'
                },
                views: {
                    'employeeLoanView@employeeLoanInfo': {
                        templateUrl: 'scripts/app/entities/ELMS/employeeLoanApplicationPending.html',
                        controller: 'EmployeeLoanApplicationPendingController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('employeeLoanRequisitionForm');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })

            .state('employeeLoanInfo.viewAndApprove', {
                parent: 'employeeLoanInfo',
                url: '/employeeLoanApplicationPending/{id}',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_INSTITUTE','ROLE_AD','ROLE_DG','ROLE_DIRECTOR','ROLE_DIRECTOR_VOC','ROLE_ADVOC','ROLE_MINISTRY'],
                    pageTitle: 'stepApp.employeeLoanRequisitionForm.home.title'
                },
                views: {
                    'employeeLoanView@employeeLoanInfo': {
                        templateUrl: 'scripts/app/entities/ELMS/employeeLoanApprove-panel.html',
                        controller: 'EmployeeLoanApprovePanelController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('employeeLoanRequisitionForm');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                     entity: ['$stateParams', 'EmployeeLoanRequisitionForm', function($stateParams, EmployeeLoanRequisitionForm) {
                            //return EmployeeLoanRequisitionForm.get({id : $stateParams.id});
                     }]
                }

            })

            .state('employeeLoanInfo.reject', {
                 parent: 'employeeLoanInfo',
                 url: '/{id}/employeeLoan-Reject',
                 data: {
                     authorities: ['ROLE_ADMIN','ROLE_DG'],
                     pageTitle: 'stepApp.employeeLoanApproveAndForward.home.title'
                 },
                 onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                     $modal.open({
                         templateUrl: 'scripts/app/entities/ELMS/employeeLoanApproveAndForward/employeeLoan-reject.html',
                         controller: 'EmployeeLoanRejectDialogController',
                         size: 'md',
                         resolve: {
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

            .state('employeeLoanInfo.employeeLoanApprovedList', {
                parent: 'employeeLoanInfo',
                url: '/loanApplicationApprovedList',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_AD','ROLE_DG','ROLE_DIRECTOR'],
                    pageTitle: 'stepApp.employeeLoanRequisitionForm.home.title'
                },
                views: {
                    'employeeLoanView@employeeLoanInfo': {
                        templateUrl: 'scripts/app/entities/ELMS/employeeLoanApprovedList.html',
                        controller: 'EmployeeLoanApprovedListController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('employeeLoanRequisitionForm');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'EmployeeLoanRequisitionForm', function($stateParams, EmployeeLoanRequisitionForm) {
                        return EmployeeLoanRequisitionForm.get({id : $stateParams.id});
                    }]
                }
            });
    });
