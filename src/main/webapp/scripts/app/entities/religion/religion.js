'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('religion', {
                parent: 'division',
                url: '/religions',
                data: {
                    authorities: ['ROLE_USER','ROLE_ADMIN'],
                    pageTitle: 'stepApp.religion.home.title'
                },
                views: {
                    'generalView@division': {
                        templateUrl: 'scripts/app/entities/religion/religions.html',
                        controller: 'ReligionController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('religion');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('religion.detail', {
                parent: 'religion',
                url: '/religion/{id}',
                data: {
                    authorities: ['ROLE_USER','ROLE_ADMIN'],
                    pageTitle: 'stepApp.religion.detail.title'
                },
                views: {
                    'generalView@division': {
                        templateUrl: 'scripts/app/entities/religion/religion-detail.html',
                        controller: 'ReligionDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('religion');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Religion', function($stateParams, Religion) {
                        return Religion.get({id : $stateParams.id});
                    }]
                }
            })
            .state('religion.new', {
                parent: 'religion',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER','ROLE_ADMIN'],
                },
                views: {
                    'generalView@division': {
                        templateUrl: 'scripts/app/entities/religion/religion-dialog.html',
                        controller: 'ReligionDialogController'
                    }
                },
                resolve: {
                    entity: function () {
                        return {
                            name: null,
                            description: null,
                            id: null
                        };
                    }
                }

            })
            .state('religion.edit', {
                parent: 'religion',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER','ROLE_ADMIN'],
                },
                views: {
                    'generalView@division': {
                        templateUrl: 'scripts/app/entities/religion/religion-dialog.html',
                        controller: 'ReligionDialogController'
                    }
                },
                resolve: {
                    entity: ['Religion','$stateParams', function(Religion,$stateParams) {
                        return Religion.get({id : $stateParams.id});
                    }]
                }
            })
            .state('religion.delete', {
                parent: 'religion',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER','ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/religion/religion-delete-dialog.html',
                        controller: 'ReligionDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Religion','$stateParams', function(Religion,$stateParams) {
                                return Religion.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('religion', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
