'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('setup.instCategory', {
                parent: 'setup',
                url: '/instCategorys',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.instCategory.home.title'
                },
                views: {
                    'setupView@setup': {
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/instCategory/instCategorys.html',
                        controller: 'InstCategoryController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instCategory');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('setup.instCategory.detail', {
                parent: 'setup.instCategory',
                url: '/instCategory/{id}',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.instCategory.detail.title'
                },
                views: {
                    'setupView@setup': {
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/instCategory/instCategory-detail.html',
                        controller: 'InstCategoryDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instCategory');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstCategory', function($stateParams, InstCategory) {
                        return InstCategory.get({id : $stateParams.id});
                    }]
                }
            })
            .state('setup.instCategory.new', {
                parent: 'setup.instCategory',
                url: '/new',
                data: {
                    authorities: []
                },

                views: {
                    'setupView@setup': {
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/instCategory/instCategory-dialog.html',
                        controller: 'InstCategoryDialogController'
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

                /*onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/instCategory/instCategory-dialog.html',
                        controller: 'InstCategoryDialogController',
                        size: 'lg',


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


                    }).result.then(function(result) {
                        $state.go('instCategory', null, { reload: true });
                    }, function() {
                        $state.go('instCategory');
                    })
                }]*/
            })
            .state('setup.instCategory.edit', {
                parent: 'setup.instCategory',
                url: '/{id}/edit',
                data: {
                    authorities: []
                },

                views: {
                    'setupView@setup': {
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/instCategory/instCategory-dialog.html',
                        controller: 'InstCategoryDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instCategory');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstCategory', function($stateParams, InstCategory) {
                        return InstCategory.get({id : $stateParams.id});
                    }]
                }

               /* onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/instCategory/instCategory-dialog.html',
                        controller: 'InstCategoryDialogController',
                        size: 'lg',


                        resolve: {
                            entity: ['InstCategory', function(InstCategory) {
                                return InstCategory.get({id : $stateParams.id});
                            }]
                        }


                    }).result.then(function(result) {
                        $state.go('instCategory', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]*/
            })
            .state('setup.instCategory.delete', {
                parent: 'setup.instCategory',
                url: '/{id}/delete',
                data: {
                    authorities: []
                },
                onEnter: ['$stateParams', '$state', '$modal','$rootScope', function($stateParams, $state, $modal,$rootScope) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/instCategory/instCategory-delete-dialog.html',
                        controller: 'InstCategoryDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['InstCategory', function(InstCategory) {
                                return InstCategory.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('setup.instCategory', null, { reload: true });
                        $rootScope.setErrorMessage('stepApp.instCategory.deleted');
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
