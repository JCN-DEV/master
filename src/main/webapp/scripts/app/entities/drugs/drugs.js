'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('drugs', {
                parent: 'entity',
                url: '/drugss',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.drugs.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/drugs/drugss.html',
                        controller: 'DrugsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('drugs');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('drugs.detail', {
                parent: 'entity',
                url: '/drugs/{id}',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.drugs.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/drugs/drugs-detail.html',
                        controller: 'DrugsDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('drugs');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Drugs', function($stateParams, Drugs) {
                        return Drugs.get({id : $stateParams.id});
                    }]
                }
            })
            .state('drugs.new', {
                parent: 'drugs',
                url: '/new',
                data: {
                    authorities: [],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/drugs/drugs-dialog.html',
                        controller: 'DrugsDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    drugsFor: null,
                                    drugClass: null,
                                    brandName: null,
                                    status: null,
                                    createBy: null,
                                    createDate: null,
                                    updateBy: null,
                                    updateDate: null,
                                    remarks: null,
                                    contains: null,
                                    dosageForm: null,
                                    manufacturer: null,
                                    price: null,
                                    types: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('drugs', null, { reload: true });
                    }, function() {
                        $state.go('drugs');
                    })
                }]
            })
            .state('drugs.edit', {
                parent: 'drugs',
                url: '/{id}/edit',
                data: {
                    authorities: [],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/drugs/drugs-dialog.html',
                        controller: 'DrugsDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Drugs', function(Drugs) {
                                return Drugs.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('drugs', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
