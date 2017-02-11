'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('setup.instEmplDesignation', {
                parent: 'setup',
                url: '/instEmplDesignations',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.instEmplDesignation.home.title'
                },
                views: {
                    'setupView@setup': {
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/instEmplDesignation/instEmplDesignations.html',
                        controller: 'InstEmplDesignationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instEmplDesignation');
                        $translatePartialLoader.addPart('designationType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('setup.instEmplDesignation.detail', {
                parent: 'setup.instEmplDesignation',
                url: '/instEmplDesignation/{id}',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.instEmplDesignation.detail.title'
                },
                views: {
                    'setupView@setup': {
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/instEmplDesignation/instEmplDesignation-detail.html',
                        controller: 'InstEmplDesignationDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instEmplDesignation');
                        $translatePartialLoader.addPart('instCategory');
                        $translatePartialLoader.addPart('instLevel');
                        $translatePartialLoader.addPart('designationType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstEmplDesignation', function($stateParams, InstEmplDesignation) {
                        return InstEmplDesignation.get({id : $stateParams.id});
                    }]
                }
            })
            .state('setup.instEmplDesignation.new', {
                parent: 'setup.instEmplDesignation',
                url: '/new',
                data: {
                    authorities: [],
                },
                 views: {
                        'setupView@setup': {
                            templateUrl: 'scripts/app/entities/instituteInformationSystem/instEmplDesignation/instEmplDesignation-dialog.html',
                            controller: 'InstEmplDesignationDialogController'
                        }
                    },

                        resolve: {
                            entity: function () {
                                return {
                                    code: null,
                                    name: null,
                                    description: null,
                                    type: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
            })
            .state('setup.instEmplDesignation.edit', {
                parent: 'setup.instEmplDesignation',
                url: '/{id}/edit',
                data: {
                    authorities: [],
                },
                 views: {
                        'setupView@setup': {
                            templateUrl: 'scripts/app/entities/instituteInformationSystem/instEmplDesignation/instEmplDesignation-dialog.html',
                            controller: 'InstEmplDesignationDialogController'
                        }
                    },

                        resolve: {
                            entity: ['InstEmplDesignation','$stateParams', function(InstEmplDesignation,$stateParams) {
                                return InstEmplDesignation.get({id : $stateParams.id});
                            }]
                        }
            })
            .state('setup.instEmplDesignation.delete', {
                parent: 'setup.instEmplDesignation',
                url: '/{id}/delete',
                data: {
                    authorities: [],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/instEmplDesignation/instEmplDesignation-delete-dialog.html',
                        controller: 'InstEmplDesignationDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['InstEmplDesignation', function(InstEmplDesignation) {
                                return InstEmplDesignation.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('setup.instEmplDesignation', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
