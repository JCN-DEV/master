'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('hrGazetteSetup', {
                parent: 'hrm',
                url: '/hrGazetteSetups',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrGazetteSetup.home.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/gazetteSetup/hrGazetteSetups.html',
                        controller: 'HrGazetteSetupController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrGazetteSetup');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('hrGazetteSetup.detail', {
                parent: 'hrm',
                url: '/hrGazetteSetup/{id}',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                    pageTitle: 'stepApp.hrGazetteSetup.detail.title'
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/gazetteSetup/hrGazetteSetup-detail.html',
                        controller: 'HrGazetteSetupDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrGazetteSetup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrGazetteSetup', function($stateParams, HrGazetteSetup) {
                        return HrGazetteSetup.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrGazetteSetup.new', {
                parent: 'hrGazetteSetup',
                url: '/new',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/gazetteSetup/hrGazetteSetup-dialog.html',
                        controller: 'HrGazetteSetupDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrGazetteSetup');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            gazetteCode: null,
                            gazetteName: null,
                            gazetteYear: null,
                            gazetteDetail: null,
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
            .state('hrGazetteSetup.edit', {
                parent: 'hrGazetteSetup',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                views: {
                    'hrmManagementView@hrm': {
                        templateUrl: 'scripts/app/entities/hrm/gazetteSetup/hrGazetteSetup-dialog.html',
                        controller: 'HrGazetteSetupDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('hrGazetteSetup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'HrGazetteSetup', function($stateParams, HrGazetteSetup) {
                        return HrGazetteSetup.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hrGazetteSetup.delete', {
                parent: 'hrGazetteSetup',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_HRM_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/hrm/gazetteSetup/hrGazetteSetup-delete-dialog.html',
                        controller: 'HrGazetteSetupDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['HrGazetteSetup', function(HrGazetteSetup) {
                                return HrGazetteSetup.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('hrGazetteSetup', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
