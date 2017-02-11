'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('vacancy', {
                parent: 'entity',
                url: '/vacancys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.vacancy.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/vacancy/vacancys.html',
                        controller: 'VacancyController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('vacancy');
                        $translatePartialLoader.addPart('employeeType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('vacancy.detail', {
                parent: 'entity',
                url: '/vacancy/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.vacancy.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/vacancy/vacancy-detail.html',
                        controller: 'VacancyDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('vacancy');
                        $translatePartialLoader.addPart('employeeType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Vacancy', function($stateParams, Vacancy) {
                        return Vacancy.get({id : $stateParams.id});
                    }]
                }
            })
            .state('vacancy.new', {
                parent: 'vacancy',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/vacancy/vacancy-dialog.html',
                        controller: 'VacancyDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    promotionGBResolutionDate: null,
                                    totalServiceTenure: null,
                                    currentJobDuration: null,
                                    status: null,
                                    remarks: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('vacancy', null, { reload: true });
                    }, function() {
                        $state.go('vacancy');
                    })
                }]
            })
            .state('vacancy.edit', {
                parent: 'vacancy',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/vacancy/vacancy-dialog.html',
                        controller: 'VacancyDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Vacancy', function(Vacancy) {
                                return Vacancy.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('vacancy', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('vacancy.delete', {
                parent: 'vacancy',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/vacancy/vacancy-delete-dialog.html',
                        controller: 'VacancyDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Vacancy', function(Vacancy) {
                                return Vacancy.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('vacancy', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
