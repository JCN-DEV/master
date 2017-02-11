'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('careerInformation', {
                parent: 'entity',
                url: '/careerInformations',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.careerInformation.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/careerInformation/careerInformations.html',
                        controller: 'CareerInformationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('careerInformation');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('careerInformation.detail', {
                parent: 'entity',
                url: '/careerInformation/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.careerInformation.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/careerInformation/careerInformation-detail.html',
                        controller: 'CareerInformationDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('careerInformation');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'CareerInformation', function($stateParams, CareerInformation) {
                        return CareerInformation.get({id : $stateParams.id});
                    }]
                }
            })
            .state('careerInformation.new', {
                parent: 'careerInformation',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/careerInformation/careerInformation-dialog.html',
                        controller: 'CareerInformationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    objectives: null,
                                    keyword: null,
                                    presentSalary: null,
                                    expectedSalary: null,
                                    lookForNature: null,
                                    availableFrom: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('careerInformation', null, { reload: true });
                    }, function() {
                        $state.go('careerInformation');
                    })
                }]
            })
            .state('careerInformation.edit', {
                parent: 'careerInformation',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/careerInformation/careerInformation-dialog.html',
                        controller: 'CareerInformationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['CareerInformation', function(CareerInformation) {
                                return CareerInformation.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('careerInformation', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('careerInformation.delete', {
                parent: 'careerInformation',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/careerInformation/careerInformation-delete-dialog.html',
                        controller: 'CareerInformationDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['CareerInformation', function(CareerInformation) {
                                return CareerInformation.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('careerInformation', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
