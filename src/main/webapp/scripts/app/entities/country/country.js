'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('country', {
                parent: 'entity',
                url: '/countries',
                data: {
                    authorities: ['ROLE_USER','ROLE_ADMIN'],
                    pageTitle: 'stepApp.country.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/country/countrys.html',
                        controller: 'CountryController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('country');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('country.detail', {
                parent: 'entity',
                url: '/country/{id}',
                data: {
                    authorities: ['ROLE_USER','ROLE_ADMIN'],
                    pageTitle: 'stepApp.country.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/country/country-detail.html',
                        controller: 'CountryDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('country');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Country', function($stateParams, Country) {
                        return Country.get({id : $stateParams.id});
                    }]
                }
            })
            .state('country.new', {
                parent: 'country',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/country/country-form.html',
                        controller: 'CountryFormController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('country');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', function($stateParams) {

                    }]
                }
            })
            .state('country.edit', {
                parent: 'country',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/country/country-form.html',
                        controller: 'CountryFormController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('country');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Country', function($stateParams, Country) {
                        return Country.get({id : $stateParams.id});
                    }]
                }
            })
            .state('country.delete', {
                parent: 'country',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/country/country-delete-dialog.html',
                        controller: 'CountryDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Country', function(Country) {
                                return Country.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('country', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
