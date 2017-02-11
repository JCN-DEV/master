'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('instPlayGroundInfoTemp', {
                parent: 'entity',
                url: '/instPlayGroundInfoTemps',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instPlayGroundInfoTemp.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instPlayGroundInfoTemp/instPlayGroundInfoTemps.html',
                        controller: 'InstPlayGroundInfoTempController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instPlayGroundInfoTemp');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instPlayGroundInfoTemp.detail', {
                parent: 'entity',
                url: '/instPlayGroundInfoTemp/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instPlayGroundInfoTemp.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instPlayGroundInfoTemp/instPlayGroundInfoTemp-detail.html',
                        controller: 'InstPlayGroundInfoTempDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instPlayGroundInfoTemp');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstPlayGroundInfoTemp', function($stateParams, InstPlayGroundInfoTemp) {
                        return InstPlayGroundInfoTemp.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instPlayGroundInfoTemp.new', {
                parent: 'instPlayGroundInfoTemp',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instPlayGroundInfoTemp/instPlayGroundInfoTemp-dialog.html',
                        controller: 'InstPlayGroundInfoTempDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    playgroundNo: null,
                                    area: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('instPlayGroundInfoTemp', null, { reload: true });
                    }, function() {
                        $state.go('instPlayGroundInfoTemp');
                    })
                }]
            })
            .state('instPlayGroundInfoTemp.edit', {
                parent: 'instPlayGroundInfoTemp',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instPlayGroundInfoTemp/instPlayGroundInfoTemp-dialog.html',
                        controller: 'InstPlayGroundInfoTempDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstPlayGroundInfoTemp', function(InstPlayGroundInfoTemp) {
                                return InstPlayGroundInfoTemp.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instPlayGroundInfoTemp', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
