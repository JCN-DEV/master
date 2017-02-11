'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('instInfraInfoTemp', {
                parent: 'entity',
                url: '/instInfraInfoTemps',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instInfraInfoTemp.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instInfraInfoTemp/instInfraInfoTemps.html',
                        controller: 'InstInfraInfoTempController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instInfraInfoTemp');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instInfraInfoTemp.detail', {
                parent: 'entity',
                url: '/instInfraInfoTemp/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instInfraInfoTemp.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instInfraInfoTemp/instInfraInfoTemp-detail.html',
                        controller: 'InstInfraInfoTempDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instInfraInfoTemp');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstInfraInfoTemp', function($stateParams, InstInfraInfoTemp) {
                        return InstInfraInfoTemp.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instInfraInfoTemp.new', {
                parent: 'instInfraInfoTemp',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instInfraInfoTemp/instInfraInfoTemp-dialog.html',
                        controller: 'InstInfraInfoTempDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('instInfraInfoTemp', null, { reload: true });
                    }, function() {
                        $state.go('instInfraInfoTemp');
                    })
                }]
            })
            .state('instInfraInfoTemp.edit', {
                parent: 'instInfraInfoTemp',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instInfraInfoTemp/instInfraInfoTemp-dialog.html',
                        controller: 'InstInfraInfoTempDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstInfraInfoTemp', function(InstInfraInfoTemp) {
                                return InstInfraInfoTemp.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instInfraInfoTemp', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
