'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('hrEmpTransferInfo', {
                parent: 'hrm',
                url: '/hrEmpTransferInfos',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrEmpTransferInfo.home.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empTransferInfo/hrEmpTransferInfos.html',
                        controller: 'HrEmpTransferInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpTransferInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrEmpTransferInfo.detail', {
                parent: 'hrm',
                url: '/hrEmpTransferInfo/{id}',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrEmpTransferInfo.detail.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empTransferInfo/hrEmpTransferInfo-detail.html',
                        controller: 'HrEmpTransferInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpTransferInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrEmpTransferInfo', function($stateParams, HrEmpTransferInfo) {
                        return HrEmpTransferInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrEmpTransferInfo.profile', {
                parent: 'hrEmpTransferInfo',
                url: '/profile',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empTransferInfo/hrEmpTransferInfo-profile.html',
                        controller: 'HrEmpTransferInfoProfileController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpTransferInfo');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrEmpTransferInfo.new', {
                parent: 'hrEmpTransferInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empTransferInfo/hrEmpTransferInfo-dialog.html',
                        controller: 'HrEmpTransferInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpTransferInfo');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            locationFrom: null,
                            locationTo: null,
                            designation: null,
                            departmentFrom: null,
                            departmentTo: null,
                            fromDate: null,
                            toDate: null,
                            officeOrderNo: null,
                            goDate: null,
                            goDoc: null,
                            goDocContentType: null,
                            goDocName: null,
                            logId:null,
                            logStatus:null,
                            logComments:null,
                            activeStatus: true,
                            createDate: null,
                            createBy: null,
                            updateDate: null,
                            updateBy: null,
                            id: null
                        };
                    }
                }
            })
            .state('hrEmpTransferInfo.edit', {
                parent: 'hrEmpTransferInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empTransferInfo/hrEmpTransferInfo-dialog.html',
                        controller: 'HrEmpTransferInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpTransferInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrEmpTransferInfo', function($stateParams, HrEmpTransferInfo) {
                        return HrEmpTransferInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrEmpTransferInfo.delete', {
                parent: 'hrEmpTransferInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/hrm/empTransferInfo/hrEmpTransferInfo-delete-dialog.html',
                        controller: 'HrEmpTransferInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['HrEmpTransferInfo', function(HrEmpTransferInfo) {
                                return HrEmpTransferInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('hrEmpTransferInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
