'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('attachmentCategory', {
                parent: 'entity',
                url: '/attachmentCategorys',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_MPOADMIN'],
                    pageTitle: 'stepApp.attachmentCategory.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/attachmentCategory/attachmentCategorys.html',
                        controller: 'AttachmentCategoryController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('attachmentCategory');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('attachmentCategory.detail', {
                parent: 'entity',
                url: '/attachmentCategory/{id}',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_MPOADMIN'],
                    pageTitle: 'stepApp.attachmentCategory.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/attachmentCategory/attachmentCategory-detail.html',
                        controller: 'AttachmentCategoryDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('attachmentCategory');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AttachmentCategory', function($stateParams, AttachmentCategory) {
                        return AttachmentCategory.get({id : $stateParams.id});
                    }]
                }
            })
            .state('attachmentCategory.new', {
                parent: 'attachmentCategory',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_MPOADMIN'],
                    pageTitle: 'stepApp.attachmentCategory.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/attachmentCategory/attachmentCategory-dialog.html',
                        controller: 'AttachmentCategoryDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('attachmentCategory');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],

                entity: ['$stateParams', 'AttachmentCategory', function($stateParams, AttachmentCategory) {

                }]
                }
            })
            .state('attachmentCategory.edit', {
                parent: 'attachmentCategory',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_MPOADMIN'],
                    pageTitle: 'stepApp.attachmentCategory.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/attachmentCategory/attachmentCategory-dialog.html',
                        controller: 'AttachmentCategoryDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('attachmentCategory');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AttachmentCategory', function($stateParams, AttachmentCategory) {
                        //return AttachmentCategory.get({id : $stateParams.id});
                    }]
                }
            })
            .state('attachmentCategory.delete', {
                parent: 'attachmentCategory',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_MPOADMIN']
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/attachmentCategory/attachmentCategory-delete-dialog.html',
                        controller: 'attachmentCategoryDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['AttachmentCategory', function(AttachmentCategory) {
                                return AttachmentCategory.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                            $state.go('attachmentCategory', null, { reload: true });
                        }, function() {
                            $state.go('^');
                        })
                }]
            });
    });
