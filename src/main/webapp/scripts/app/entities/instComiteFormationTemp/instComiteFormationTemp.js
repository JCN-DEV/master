'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('instComiteFormationTemp', {
                parent: 'entity',
                url: '/instComiteFormationTemps',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instComiteFormationTemp.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instComiteFormationTemp/instComiteFormationTemps.html',
                        controller: 'InstComiteFormationTempController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instComiteFormationTemp');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instComiteFormationTemp.detail', {
                parent: 'entity',
                url: '/instComiteFormationTemp/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instComiteFormationTemp.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instComiteFormationTemp/instComiteFormationTemp-detail.html',
                        controller: 'InstComiteFormationTempDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instComiteFormationTemp');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstComiteFormationTemp', function($stateParams, InstComiteFormationTemp) {
                        return InstComiteFormationTemp.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instComiteFormationTemp.new', {
                parent: 'instComiteFormationTemp',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instComiteFormationTemp/instComiteFormationTemp-dialog.html',
                        controller: 'InstComiteFormationTempDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    comiteName: null,
                                    comiteType: null,
                                    address: null,
                                    timeFrom: null,
                                    timeTo: null,
                                    formationDate: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('instComiteFormationTemp', null, { reload: true });
                    }, function() {
                        $state.go('instComiteFormationTemp');
                    })
                }]
            })
            .state('instComiteFormationTemp.edit', {
                parent: 'instComiteFormationTemp',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instComiteFormationTemp/instComiteFormationTemp-dialog.html',
                        controller: 'InstComiteFormationTempDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstComiteFormationTemp', function(InstComiteFormationTemp) {
                                return InstComiteFormationTemp.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instComiteFormationTemp', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
