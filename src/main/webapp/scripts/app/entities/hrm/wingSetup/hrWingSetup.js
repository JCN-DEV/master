'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('hrWingSetup', {
                parent: 'hrm',
                url: '/hrWingSetups',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrWingSetup.home.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/wingSetup/hrWingSetups.html',
                        controller: 'HrWingSetupController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrWingSetup');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrWingSetup.detail', {
                parent: 'hrm',
                url: '/hrWingSetup/{id}',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrWingSetup.detail.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/wingSetup/hrWingSetup-detail.html',
                        controller: 'HrWingSetupDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrWingSetup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrWingSetup', function($stateParams, HrWingSetup) {
                        return HrWingSetup.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrWingSetup.new', {
                parent: 'hrWingSetup',
                url: '/new',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/wingSetup/hrWingSetup-dialog.html',
                        controller: 'HrWingSetupDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrWingSetup');
                        $translatePartialLoader.addPart('hrWingHeadSetup');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            wingName: null,
                            wingDesc: null,
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
            .state('hrWingSetup.edit', {
                parent: 'hrWingSetup',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/wingSetup/hrWingSetup-dialog.html',
                        controller: 'HrWingSetupDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrWingSetup');
                        $translatePartialLoader.addPart('hrWingHeadSetup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrWingSetup', function($stateParams, HrWingSetup) {
                        return HrWingSetup.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrWingSetup.delete', {
                parent: 'hrWingSetup',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/hrm/wingSetup/hrWingSetup-delete-dialog.html',
                        controller: 'HrWingSetupDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['HrWingSetup', function(HrWingSetup) {
                                return HrWingSetup.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('hrWingSetup', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
