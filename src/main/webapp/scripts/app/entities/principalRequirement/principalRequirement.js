'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('principalRequirement', {
                parent: 'entity',
                url: '/principalRequirements',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.principalRequirement.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/principalRequirement/principalRequirements.html',
                        controller: 'PrincipalRequirementController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('principalRequirement');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('principalRequirement.detail', {
                parent: 'entity',
                url: '/principalRequirement/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.principalRequirement.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/principalRequirement/principalRequirement-detail.html',
                        controller: 'PrincipalRequirementDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('principalRequirement');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PrincipalRequirement', function($stateParams, PrincipalRequirement) {
                        return PrincipalRequirement.get({id : $stateParams.id});
                    }]
                }
            })
            .state('principalRequirement.new', {
                parent: 'principalRequirement',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/principalRequirement/principalRequirement-dialog.html',
                        controller: 'PrincipalRequirementDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    firstJoiningDateAsLecturer: null,
                                    firstMPOEnlistingDateAsLecturer: null,
                                    firstJoiningDateAsAsstProf: null,
                                    firstMPOEnlistingDateAsstProf: null,
                                    firstJoiningDateAsVP: null,
                                    firstMPOEnlistingDateAsVP: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('principalRequirement', null, { reload: true });
                    }, function() {
                        $state.go('principalRequirement');
                    })
                }]
            })
            .state('principalRequirement.edit', {
                parent: 'principalRequirement',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/principalRequirement/principalRequirement-dialog.html',
                        controller: 'PrincipalRequirementDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['PrincipalRequirement', function(PrincipalRequirement) {
                                return PrincipalRequirement.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('principalRequirement', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('principalRequirement.delete', {
                parent: 'principalRequirement',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/principalRequirement/principalRequirement-delete-dialog.html',
                        controller: 'PrincipalRequirementDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['PrincipalRequirement', function(PrincipalRequirement) {
                                return PrincipalRequirement.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('principalRequirement', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
