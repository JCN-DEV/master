'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('assetAccuisitionSetup', {
                parent: 'entity',
                url: '/assetAccuisitionSetups',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.assetAccuisitionSetup.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/assetAccuisitionSetup/assetAccuisitionSetups.html',
                        controller: 'AssetAccuisitionSetupController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('assetAccuisitionSetup');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('assetAccuisitionSetup.detail', {
                parent: 'entity',
                url: '/assetAccuisitionSetup/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.assetAccuisitionSetup.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/assetAccuisitionSetup/assetAccuisitionSetup-detail.html',
                        controller: 'AssetAccuisitionSetupDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('assetAccuisitionSetup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AssetAccuisitionSetup', function($stateParams, AssetAccuisitionSetup) {
                        return AssetAccuisitionSetup.get({id : $stateParams.id});
                    }]
                }
            })
            .state('assetAccuisitionSetup.new', {
                parent: 'assetAccuisitionSetup',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/assetAccuisitionSetup/assetAccuisitionSetup-dialog.html',
                        controller: 'AssetAccuisitionSetupDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    trackID: null,
                                    code: null,
                                    name: null,
                                    Description: null,
                                    createDate: null,
                                    createBy: null,
                                    updateDate: null,
                                    updateBy: null,
                                    remarks: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('assetAccuisitionSetup', null, { reload: true });
                    }, function() {
                        $state.go('assetAccuisitionSetup');
                    })
                }]
            })
            .state('assetAccuisitionSetup.edit', {
                parent: 'assetAccuisitionSetup',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/assetAccuisitionSetup/assetAccuisitionSetup-dialog.html',
                        controller: 'AssetAccuisitionSetupDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['AssetAccuisitionSetup', function(AssetAccuisitionSetup) {
                                return AssetAccuisitionSetup.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('assetAccuisitionSetup', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
