'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('hrPayScaleSetup', {
                parent: 'hrm',
                url: '/hrPayScaleSetups',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrPayScaleSetup.home.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/payScaleSetup/hrPayScaleSetups.html',
                        controller: 'HrPayScaleSetupController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrPayScaleSetup');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrPayScaleSetup.detail', {
                parent: 'hrm',
                url: '/hrPayScaleSetup/{id}',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrPayScaleSetup.detail.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/payScaleSetup/hrPayScaleSetup-detail.html',
                        controller: 'HrPayScaleSetupDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrPayScaleSetup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrPayScaleSetup', function($stateParams, HrPayScaleSetup) {
                        return HrPayScaleSetup.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrPayScaleSetup.new', {
                parent: 'hrPayScaleSetup',
                url: '/new',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/payScaleSetup/hrPayScaleSetup-dialog.html',
                        controller: 'HrPayScaleSetupDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrPayScaleSetup');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            payScaleCode: null,
                            basicPayScale: null,
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
            .state('hrPayScaleSetup.edit', {
                parent: 'hrPayScaleSetup',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/payScaleSetup/hrPayScaleSetup-dialog.html',
                        controller: 'HrPayScaleSetupDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrPayScaleSetup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrPayScaleSetup', function($stateParams, HrPayScaleSetup) {
                        return HrPayScaleSetup.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrPayScaleSetup.delete', {
                parent: 'hrPayScaleSetup',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/hrm/payScaleSetup/hrPayScaleSetup-delete-dialog.html',
                        controller: 'HrPayScaleSetupDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['HrPayScaleSetup', function(HrPayScaleSetup) {
                                return HrPayScaleSetup.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('hrPayScaleSetup', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
