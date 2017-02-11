'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('instLabInfoTemp', {
                parent: 'entity',
                url: '/instLabInfoTemps',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instLabInfoTemp.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instLabInfoTemp/instLabInfoTemps.html',
                        controller: 'InstLabInfoTempController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instLabInfoTemp');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instLabInfoTemp.detail', {
                parent: 'entity',
                url: '/instLabInfoTemp/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instLabInfoTemp.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instLabInfoTemp/instLabInfoTemp-detail.html',
                        controller: 'InstLabInfoTempDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instLabInfoTemp');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstLabInfoTemp', function($stateParams, InstLabInfoTemp) {
                        return InstLabInfoTemp.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instLabInfoTemp.new', {
                parent: 'instLabInfoTemp',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instLabInfoTemp/instLabInfoTemp-dialog.html',
                        controller: 'InstLabInfoTempDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    nameOrNumber: null,
                                    buildingNameOrNumber: null,
                                    length: null,
                                    width: null,
                                    totalBooks: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('instLabInfoTemp', null, { reload: true });
                    }, function() {
                        $state.go('instLabInfoTemp');
                    })
                }]
            })
            .state('instLabInfoTemp.edit', {
                parent: 'instLabInfoTemp',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instLabInfoTemp/instLabInfoTemp-dialog.html',
                        controller: 'InstLabInfoTempDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstLabInfoTemp', function(InstLabInfoTemp) {
                                return InstLabInfoTemp.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instLabInfoTemp', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
