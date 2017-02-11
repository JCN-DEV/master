'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('hrEmpAddressInfo', {
                parent: 'hrm',
                url: '/hrEmpAddressInfos',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrEmpAddressInfo.home.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empAddressInfo/hrEmpAddressInfos.html',
                        controller: 'HrEmpAddressInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpAddressInfo');
                        $translatePartialLoader.addPart('addressTypes');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrEmpAddressInfo.detail', {
                parent: 'hrm',
                url: '/hrEmpAddressInfo/{id}',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrEmpAddressInfo.detail.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empAddressInfo/hrEmpAddressInfo-detail.html',
                        controller: 'HrEmpAddressInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpAddressInfo');
                        $translatePartialLoader.addPart('addressTypes');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrEmpAddressInfo', function($stateParams, HrEmpAddressInfo) {
                        return HrEmpAddressInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrEmpAddressInfo.profile', {
                parent: 'hrEmpAddressInfo',
                url: '/profile',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empAddressInfo/hrEmpAddressInfo-profile.html',
                        controller: 'HrEmpAddressInfoProfileController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpAddressInfo');
                        $translatePartialLoader.addPart('addressTypes');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrEmpAddressInfo.new', {
                parent: 'hrEmpAddressInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empAddressInfo/hrEmpAddressInfo-dialog.html',
                        controller: 'HrEmpAddressInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpAddressInfo');
                        $translatePartialLoader.addPart('addressTypes');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            addressType: null,
                            houseNumber: null,
                            houseNumberBn: null,
                            roadNumber: null,
                            roadNumberBn: null,
                            villageName: null,
                            villageNameBn: null,
                            postOffice: null,
                            postOfficeBn: null,
                            contactNumber: null,
                            activeStatus: true,
                            logId:0,
                            logStatus:1,
                            logComments:null,
                            createDate: null,
                            createBy: null,
                            updateDate: null,
                            updateBy: null,
                            id: null
                        };
                    }
                }
            })
            .state('hrEmpAddressInfo.edit', {
                parent: 'hrEmpAddressInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empAddressInfo/hrEmpAddressInfo-dialog.html',
                        controller: 'HrEmpAddressInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpAddressInfo');
                        $translatePartialLoader.addPart('addressTypes');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrEmpAddressInfo', function($stateParams, HrEmpAddressInfo) {
                        return HrEmpAddressInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrEmpAddressInfo.delete', {
                parent: 'hrEmpAddressInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/hrm/empAddressInfo/hrEmpAddressInfo-delete-dialog.html',
                        controller: 'HrEmpAddressInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['HrEmpAddressInfo', function(HrEmpAddressInfo) {
                                return HrEmpAddressInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('hrEmpAddressInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('hrEmpAddressInfo.addrappr', {
                parent: 'hrm',
                url: '/{id}/addrappr',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/hrm/empAddressInfo/hrEmpAddressInfo-approval.html',
                        controller: 'HrEmpAddressInfoApprovalController',
                        size: 'md',
                        resolve: {
                            translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                                $translatePartialLoader.addPart('hrEmpAddressInfo');
                                $translatePartialLoader.addPart('addressTypes');
                                return $translate.refresh();
                            }]
                        }
                    }).result.then(function(result) {
                            $state.go('hrm', null, { reload: true });
                        }, function() {
                            $state.go('hrm');
                        })
                }]
            });
    });
