'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('dlBookRequisition', {
                parent: 'libraryInfo',
                url: '/dlBookRequisitions',
                data: {
                    authorities: ['ROLE_USER','ROLE_ADMIN','ROLE_INSTITUTE'],
                    pageTitle: 'stepApp.dlBookRequisition.home.title'
                },
                views: {
                    'libraryView@libraryInfo': {
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlBookRequisition/dlBookRequisitions.html',
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
                parent: 'dlBookRequisition',
                url: '/dlBookRequisition/{id}',
                data: {
                    authorities: ['ROLE_USER','ROLE_INSTITUTE'],
                    pageTitle: 'stepApp.dlBookRequisition.detail.title'
                },
                views: {
                    'libraryView@libraryInfo': {
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlBookRequisition/dlBookRequisition-detail.html',
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
                    authorities: ['ROLE_USER','ROLE_GOVT_STUDENT']
                },

                views: {
                    'libraryView@libraryInfo': {
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlBookRequisition/dlBookRequisition-dialog.html',
                        controller: 'DlBookRequisitionDialogController',
                    }
                },resolve: {
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
                //onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                //    $modal.open({
                //        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlBookRequisition/dlBookRequisition-dialog.html',
                //        controller: 'DlBookRequisitionDialogController',
                //        size: 'lg',
                //
                //    }).result.then(function(result) {
                //        $state.go('dlBookRequisition', null, { reload: true });
                //    }, function() {
                //        $state.go('dlBookRequisition');
                //    })
                //}]
            })
            .state('dlBookRequisition.edit', {
                parent: 'dlBookRequisition',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER','ROLE_INSTITUTE']
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/DigitalLibraryManagement/dlBookRequisition/dlBookRequisition-dialog.html',
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
