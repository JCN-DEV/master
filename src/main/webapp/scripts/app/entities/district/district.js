'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('division.district', {
                parent: 'division',
                url: '/districts',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.district.home.title'
                },
                views: {
                    'generalView@division': {
                        templateUrl: 'scripts/app/entities/district/districts.html',
                        controller: 'DistrictController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('district');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('division.district.detail', {
                parent: 'division.district',
                url: '/district/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.district.detail.title'
                },
                views: {
                    'generalView@division': {
                        templateUrl: 'scripts/app/entities/district/district-detail.html',
                        controller: 'DistrictDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('district');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'District', function($stateParams, District) {
                        return District.get({id : $stateParams.id});
                    }]
                }
            })
            .state('division.district.new', {
                parent: 'division.district',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/district/district-dialog.html',
                        controller: 'DistrictDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    bnName: null,
                                    lat: null,
                                    lon: null,
                                    website: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('division.district', null, { reload: true });
                    }, function() {
                        $state.go('division.district');
                    })
                }]
            })
            .state('division.district.edit', {
                parent: 'division.district',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/district/district-dialog.html',
                        controller: 'DistrictDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['District', function(District) {
                                return District.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('district', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('division.district.delete', {
                parent: 'division.district',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/district/district-delete-dialog.html',
                        controller: 'DistrictDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['District', function(District) {
                                return District.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('district', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
