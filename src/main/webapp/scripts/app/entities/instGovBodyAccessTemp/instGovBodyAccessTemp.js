'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('instGovBodyAccessTemp', {
                parent: 'entity',
                url: '/instGovBodyAccessTemps',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instGovBodyAccessTemp.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instGovBodyAccessTemp/instGovBodyAccessTemps.html',
                        controller: 'InstGovBodyAccessTempController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instGovBodyAccessTemp');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instGovBodyAccessTemp.detail', {
                parent: 'entity',
                url: '/instGovBodyAccessTemp/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instGovBodyAccessTemp.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instGovBodyAccessTemp/instGovBodyAccessTemp-detail.html',
                        controller: 'InstGovBodyAccessTempDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instGovBodyAccessTemp');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstGovBodyAccessTemp', function($stateParams, InstGovBodyAccessTemp) {
                        return InstGovBodyAccessTemp.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instGovBodyAccessTemp.new', {
                parent: 'instGovBodyAccessTemp',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instGovBodyAccessTemp/instGovBodyAccessTemp-dialog.html',
                        controller: 'InstGovBodyAccessTempDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    dateCreated: null,
                                    dateModified: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('instGovBodyAccessTemp', null, { reload: true });
                    }, function() {
                        $state.go('instGovBodyAccessTemp');
                    })
                }]
            })
            .state('instGovBodyAccessTemp.edit', {
                parent: 'instGovBodyAccessTemp',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instGovBodyAccessTemp/instGovBodyAccessTemp-dialog.html',
                        controller: 'InstGovBodyAccessTempDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstGovBodyAccessTemp', function(InstGovBodyAccessTemp) {
                                return InstGovBodyAccessTemp.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instGovBodyAccessTemp', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
