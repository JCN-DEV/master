'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('hrEmpProfMemberInfo', {
                parent: 'hrm',
                url: '/hrEmpProfMemberInfos',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrEmpProfMemberInfo.home.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empProfMemberInfo/hrEmpProfMemberInfos.html',
                        controller: 'HrEmpProfMemberInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpProfMemberInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrEmpProfMemberInfo.detail', {
                parent: 'hrm',
                url: '/hrEmpProfMemberInfo/{id}',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrEmpProfMemberInfo.detail.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empProfMemberInfo/hrEmpProfMemberInfo-detail.html',
                        controller: 'HrEmpProfMemberInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpProfMemberInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrEmpProfMemberInfo', function($stateParams, HrEmpProfMemberInfo) {
                        return HrEmpProfMemberInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrEmpProfMemberInfo.profile', {
                parent: 'hrEmpProfMemberInfo',
                url: '/profile',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empProfMemberInfo/hrEmpProfMemberInfo-profile.html',
                        controller: 'HrEmpProfMemberInfoProfileController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpProfMemberInfo');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrEmpProfMemberInfo.new', {
                parent: 'hrEmpProfMemberInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empProfMemberInfo/hrEmpProfMemberInfo-dialog.html',
                        controller: 'HrEmpProfMemberInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpProfMemberInfo');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            organizationName: null,
                            membershipNumber: null,
                            membershipDate: null,
                            logId: null,
                            logStatus: null,
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
            .state('hrEmpProfMemberInfo.edit', {
                parent: 'hrEmpProfMemberInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empProfMemberInfo/hrEmpProfMemberInfo-dialog.html',
                        controller: 'HrEmpProfMemberInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpProfMemberInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrEmpProfMemberInfo', function($stateParams, HrEmpProfMemberInfo) {
                        return HrEmpProfMemberInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrEmpProfMemberInfo.delete', {
                parent: 'hrEmpProfMemberInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/hrm/empProfMemberInfo/hrEmpProfMemberInfo-delete-dialog.html',
                        controller: 'HrEmpProfMemberInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['HrEmpProfMemberInfo', function(HrEmpProfMemberInfo) {
                                return HrEmpProfMemberInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('hrEmpProfMemberInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('hrEmpProfMemberInfo.profmemappr', {
                parent: 'hrm',
                url: '/{id}/profmemappr',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/hrm/empProfMemberInfo/hrEmpProfMemberInfo-approval.html',
                        controller: 'HrEmpProfMemberInfoApprovalController',
                        size: 'md',
                        resolve: {

                        }
                    }).result.then(function(result) {
                            $state.go('hrm', null, { reload: true });
                        }, function() {
                            $state.go('hrm');
                        })
                }]
            });
    });
