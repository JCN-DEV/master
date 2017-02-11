'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('professionalQualification', {
                parent: 'entity',
                url: '/professionalQualifications',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.professionalQualification.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/professionalQualification/professionalQualifications.html',
                        controller: 'ProfessionalQualificationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('professionalQualification');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('professionalQualification.detail', {
                parent: 'entity',
                url: '/professionalQualification/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.professionalQualification.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/professionalQualification/professionalQualification-detail.html',
                        controller: 'ProfessionalQualificationDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('professionalQualification');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'ProfessionalQualification', function($stateParams, ProfessionalQualification) {
                        return ProfessionalQualification.get({id : $stateParams.id});
                    }]
                }
            })
            .state('professionalQualification.new', {
                parent: 'professionalQualification',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/professionalQualification/professionalQualification-dialog.html',
                        controller: 'ProfessionalQualificationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    location: null,
                                    dateFrom: null,
                                    dateTo: null,
                                    duration: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('professionalQualification', null, { reload: true });
                    }, function() {
                        $state.go('professionalQualification');
                    })
                }]
            })
            .state('professionalQualification.edit', {
                parent: 'professionalQualification',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/professionalQualification/professionalQualification-dialog.html',
                        controller: 'ProfessionalQualificationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['ProfessionalQualification', function(ProfessionalQualification) {
                                return ProfessionalQualification.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('professionalQualification', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('professionalQualification.delete', {
                parent: 'professionalQualification',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/professionalQualification/professionalQualification-delete-dialog.html',
                        controller: 'ProfessionalQualificationDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['ProfessionalQualification', function(ProfessionalQualification) {
                                return ProfessionalQualification.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('professionalQualification', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
