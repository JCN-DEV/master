'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('dlBookRequisition', {
                parent: 'entity',
                url: '/dlBookRequisitions',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.dlBookRequisition.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/dlBookRequisition/dlBookRequisitions.html',
                        controller: 'DlBookRequisitionController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dlBookRequisition');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('dlBookRequisition.detail', {
                parent: 'entity',
                url: '/dlBookRequisition/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.dlBookRequisition.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/dlBookRequisition/dlBookRequisition-detail.html',
                        controller: 'DlBookRequisitionDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('dlBookRequisition');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'DlBookRequisition', function($stateParams, DlBookRequisition) {
                        return DlBookRequisition.get({id : $stateParams.id});
                    }]
                }
            })
            .state('dlBookRequisition.new', {
                parent: 'dlBookRequisition',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/dlBookRequisition/dlBookRequisition-dialog.html',
                        controller: 'DlBookRequisitionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    title: null,
                                    edition: null,
                                    authorName: null,
                                    createDate: null,
                                    updateDate: null,
                                    createBy: null,
                                    updateBy: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('dlBookRequisition', null, { reload: true });
                    }, function() {
                        $state.go('dlBookRequisition');
                    })
                }]
            })
            .state('dlBookRequisition.edit', {
                parent: 'dlBookRequisition',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/dlBookRequisition/dlBookRequisition-dialog.html',
                        controller: 'DlBookRequisitionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['DlBookRequisition', function(DlBookRequisition) {
                                return DlBookRequisition.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('dlBookRequisition', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
