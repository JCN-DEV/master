'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('hrEmpChildrenInfo', {
                parent: 'hrm',
                url: '/hrEmpChildrenInfos',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrEmpChildrenInfo.home.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empChildrenInfo/hrEmpChildrenInfos.html',
                        controller: 'HrEmpChildrenInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpChildrenInfo');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrEmpChildrenInfo.detail', {
                parent: 'hrm',
                url: '/hrEmpChildrenInfo/{id}',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrEmpChildrenInfo.detail.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empChildrenInfo/hrEmpChildrenInfo-detail.html',
                        controller: 'HrEmpChildrenInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpChildrenInfo');
                        $translatePartialLoader.addPart('gender');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrEmpChildrenInfo', function($stateParams, HrEmpChildrenInfo) {
                        return HrEmpChildrenInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrEmpChildrenInfo.profile', {
                parent: 'hrEmpChildrenInfo',
                url: '/profile',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empChildrenInfo/hrEmpChildrenInfo-profile.html',
                        controller: 'HrEmpChildrenInfoProfileController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpChildrenInfo');
                        $translatePartialLoader.addPart('gender');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrEmpChildrenInfo.new', {
                parent: 'hrEmpChildrenInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empChildrenInfo/hrEmpChildrenInfo-dialog.html',
                        controller: 'HrEmpChildrenInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpChildrenInfo');
                        $translatePartialLoader.addPart('gender');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            childrenName: null,
                            childrenNameBn: null,
                            dateOfBirth: null,
                            gender: null,
                            activeStatus: false,
                            createDate: null,
                            createBy: null,
                            updateDate: null,
                            updateBy: null,
                            id: null
                        };
                    }
                }
            })
            .state('hrEmpChildrenInfo.edit', {
                parent: 'hrEmpChildrenInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empChildrenInfo/hrEmpChildrenInfo-dialog.html',
                        controller: 'HrEmpChildrenInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpChildrenInfo');
                        $translatePartialLoader.addPart('gender');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrEmpChildrenInfo', function($stateParams, HrEmpChildrenInfo) {
                        return HrEmpChildrenInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrEmpChildrenInfo.delete', {
                parent: 'hrEmpChildrenInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/hrm/empChildrenInfo/hrEmpChildrenInfo-delete-dialog.html',
                        controller: 'HrEmpChildrenInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['HrEmpChildrenInfo', function(HrEmpChildrenInfo) {
                                return HrEmpChildrenInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('hrEmpChildrenInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('hrEmpChildrenInfo.childappr', {
                parent: 'hrm',
                url: '/{id}/childappr',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/hrm/empChildrenInfo/hrEmpChildrenInfo-approval.html',
                        controller: 'HrEmpChildrenInfoApprovalController',
                        size: 'md',
                        resolve: {
                            translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                                $translatePartialLoader.addPart('hrEmpChildrenInfo');
                                $translatePartialLoader.addPart('gender');
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
