'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('instCategoryTemp', {
                parent: 'entity',
                url: '/instCategoryTemps',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instCategoryTemp.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instCategoryTemp/instCategoryTemps.html',
                        controller: 'InstCategoryTempController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instCategoryTemp');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instCategoryTemp.detail', {
                parent: 'entity',
                url: '/instCategoryTemp/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instCategoryTemp.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instCategoryTemp/instCategoryTemp-detail.html',
                        controller: 'InstCategoryTempDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instCategoryTemp');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstCategoryTemp', function($stateParams, InstCategoryTemp) {
                        return InstCategoryTemp.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instCategoryTemp.new', {
                parent: 'instCategoryTemp',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instCategoryTemp/instCategoryTemp-dialog.html',
                        controller: 'InstCategoryTempDialogController',
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
                        $state.go('instCategoryTemp', null, { reload: true });
                    }, function() {
                        $state.go('instCategoryTemp');
                    })
                }]
            })
            .state('instCategoryTemp.edit', {
                parent: 'instCategoryTemp',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instCategoryTemp/instCategoryTemp-dialog.html',
                        controller: 'InstCategoryTempDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstCategoryTemp', function(InstCategoryTemp) {
                                return InstCategoryTemp.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instCategoryTemp', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
