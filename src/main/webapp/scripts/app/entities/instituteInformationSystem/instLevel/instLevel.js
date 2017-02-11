'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('setup.instLevel', {
                parent: 'setup',
                url: '/instLevels',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.instLevel.home.title'
                },
                views: {
                    'setupView@setup': {
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/instLevel/instLevels.html',
                        controller: 'InstLevelController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instLevel');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('setup.instLevel.detail', {
                parent: 'setup.instLevel',
                url: '/instLevel/{id}',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.instLevel.detail.title'
                },
                views: {
                    'setupView@setup': {
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/instLevel/instLevel-detail.html',
                        controller: 'InstLevelDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instLevel');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstLevel', function($stateParams, InstLevel) {
                        return InstLevel.get({id : $stateParams.id});
                    }]
                }
            })
            .state('setup.instLevel.new', {
                parent: 'setup.instLevel',
                url: '/new',
                data: {
                    authorities: []
                },
                 views: {
                        'setupView@setup': {
                          templateUrl: 'scripts/app/entities/instituteInformationSystem/instLevel/instLevel-dialog.html',
                          controller: 'InstLevelDialogController'
                        }
                    },

                        resolve: {
                            entity: function () {
                                return {
                                    code: null,
                                    name: null,
                                    description: null,
                                    pStatus: null,
                                    createdDate: null,
                                    updatedDate: null,
                                    createdBy: null,
                                    updatedBy: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }

            })
            .state('setup.instLevel.edit', {
                parent: 'setup.instLevel',
                url: '/{id}/edit',
                data: {
                    authorities: []
                },
                 views: {
                        'setupView@setup': {
                          templateUrl: 'scripts/app/entities/instituteInformationSystem/instLevel/instLevel-dialog.html',
                          controller: 'InstLevelDialogController'
                        }
                    },

                        resolve: {
                            entity: ['InstLevel','$stateParams', function(InstLevel,$stateParams) {
                                return InstLevel.get({id : $stateParams.id});
                            }]
                        }

            })
            .state('setup.instLevel.delete', {
                parent: 'setup.instLevel',
                url: '/{id}/delete',
                data: {
                    authorities: []
                },
                onEnter: ['$stateParams', '$state', '$modal', '$rootScope', function($stateParams, $state, $modal,$rootScope) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/instLevel/instLevel-delete-dialog.html',
                        controller: 'InstLevelDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['InstLevel', function(InstLevel) {
                                return InstLevel.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('setup.instLevel', null, { reload: true });
                            $rootScope.setErrorMessage('stepApp.instLevel.deleted');
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
