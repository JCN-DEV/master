'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('hrEmpTransferApplInfo', {
                parent: 'hrm',
                url: '/hrEmpTransferApplInfos',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrEmpTransferApplInfo.home.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empTransferApplInfo/hrEmpTransferApplInfos.html',
                        controller: 'HrEmpTransferApplInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpTransferApplInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrEmpTransferApplInfo.detail', {
                parent: 'hrm',
                url: '/hrEmpTransferApplInfo/{id}',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrEmpTransferApplInfo.detail.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empTransferApplInfo/hrEmpTransferApplInfo-detail.html',
                        controller: 'HrEmpTransferApplInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpTransferApplInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrEmpTransferApplInfo', function($stateParams, HrEmpTransferApplInfo) {
                        return HrEmpTransferApplInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrEmpTransferApplInfo.new', {
                parent: 'hrEmpTransferApplInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empTransferApplInfo/hrEmpTransferApplInfo-dialog.html',
                        controller: 'HrEmpTransferApplInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpTransferApplInfo');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            transferReason: null,
                            officeOrderNumber: null,
                            officeOrderDate: null,
                            activeStatus: true,
                            selectedOption: 0,
                            logId: null,
                            logStatus: null,
                            logComments: null,
                            createDate: null,
                            createBy: null,
                            updateDate: null,
                            updateBy: null,
                            id: null
                        };
                    }
                }
            })
            .state('hrEmpTransferApplInfo.appl', {
                parent: 'hrEmpTransferApplInfo',
                url: '/appl',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empTransferApplInfo/hrEmpTransferApplInfo-appl.html',
                        controller: 'HrEmpTransferApplInfoApplicationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpTransferApplInfo');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            transferReason: null,
                            officeOrderNumber: null,
                            officeOrderDate: null,
                            activeStatus: true,
                            selectedOption: 0,
                            logId: null,
                            logStatus: null,
                            logComments: null,
                            createDate: null,
                            createBy: null,
                            updateDate: null,
                            updateBy: null,
                            id: null
                        };
                    }
                }
            })
            .state('hrEmpTransferApplInfo.edit', {
                parent: 'hrEmpTransferApplInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empTransferApplInfo/hrEmpTransferApplInfo-dialog.html',
                        controller: 'HrEmpTransferApplInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpTransferApplInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrEmpTransferApplInfo', function($stateParams, HrEmpTransferApplInfo) {
                        return HrEmpTransferApplInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrEmpTransferApplInfo.appr', {
                parent: 'hrm',
                url: '/{id}/applapprv',
                data: {
                    authorities: ['ROLE_HRM_USER']
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/hrm/empTransferApplInfo/hrEmpTransferApplInfo-approval.html',
                        controller: 'HrEmpTransferApplInfoApprovalController',
                        size: 'md',
                        resolve: {
                            translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                                $translatePartialLoader.addPart('hrEmpTransferApplInfo');
                                $translatePartialLoader.addPart('hrEmpServiceHistory');
                                return $translate.refresh();
                            }],
                            entity: ['HrEmpTransferApplInfo', function(HrEmpTransferApplInfo) {
                                return HrEmpTransferApplInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                            $state.go('hrm', null, { reload: true });
                        }, function() {
                            $state.go('hrm');
                        })
                }]
            })
            .state('hrEmpTransferApplInfo.delete', {
                parent: 'hrEmpTransferApplInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/hrm/empTransferApplInfo/hrEmpTransferApplInfo-delete-dialog.html',
                        controller: 'HrEmpTransferApplInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['HrEmpTransferApplInfo', function(HrEmpTransferApplInfo) {
                                return HrEmpTransferApplInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('hrEmpTransferApplInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
