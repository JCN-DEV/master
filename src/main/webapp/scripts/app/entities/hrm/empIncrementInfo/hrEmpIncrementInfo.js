'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('hrEmpIncrementInfo', {
                parent: 'hrm',
                url: '/hrEmpIncrementInfos',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrEmpIncrementInfo.home.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empIncrementInfo/hrEmpIncrementInfos.html',
                        controller: 'HrEmpIncrementInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpIncrementInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrEmpIncrementInfo.detail', {
                parent: 'hrm',
                url: '/hrEmpIncrementInfo/{id}',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrEmpIncrementInfo.detail.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empIncrementInfo/hrEmpIncrementInfo-detail.html',
                        controller: 'HrEmpIncrementInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpIncrementInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrEmpIncrementInfo', function($stateParams, HrEmpIncrementInfo) {
                        return HrEmpIncrementInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrEmpIncrementInfo.profile', {
                parent: 'hrEmpIncrementInfo',
                url: '/profile',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empIncrementInfo/hrEmpIncrementInfo-profile.html',
                        controller: 'HrEmpIncrementInfoProfileController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpIncrementInfo');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrEmpIncrementInfo.new', {
                parent: 'hrEmpIncrementInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empIncrementInfo/hrEmpIncrementInfo-dialog.html',
                        controller: 'HrEmpIncrementInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpIncrementInfo');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            incrementAmount: null,
                            incrementDate: null,
                            basic: null,
                            basicDate: null,
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
            .state('hrEmpIncrementInfo.edit', {
                parent: 'hrEmpIncrementInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/empIncrementInfo/hrEmpIncrementInfo-dialog.html',
                        controller: 'HrEmpIncrementInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrEmpIncrementInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrEmpIncrementInfo', function($stateParams, HrEmpIncrementInfo) {
                        return HrEmpIncrementInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrEmpIncrementInfo.delete', {
                parent: 'hrEmpIncrementInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/hrm/empIncrementInfo/hrEmpIncrementInfo-delete-dialog.html',
                        controller: 'HrEmpIncrementInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['HrEmpIncrementInfo', function(HrEmpIncrementInfo) {
                                return HrEmpIncrementInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('hrEmpIncrementInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
