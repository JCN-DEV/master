'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('employeeLoanAttachment', {
                parent: 'entity',
                url: '/employeeLoanAttachments',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.employeeLoanAttachment.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/employeeLoanAttachment/employeeLoanAttachments.html',
                        controller: 'EmployeeLoanAttachmentController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('employeeLoanAttachment');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('employeeLoanAttachment.detail', {
                parent: 'entity',
                url: '/employeeLoanAttachment/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.employeeLoanAttachment.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/employeeLoanAttachment/employeeLoanAttachment-detail.html',
                        controller: 'EmployeeLoanAttachmentDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('employeeLoanAttachment');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'EmployeeLoanAttachment', function($stateParams, EmployeeLoanAttachment) {
                        return EmployeeLoanAttachment.get({id : $stateParams.id});
                    }]
                }
            })
            .state('employeeLoanAttachment.new', {
                parent: 'employeeLoanAttachment',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/employeeLoanAttachment/employeeLoanAttachment-dialog.html',
                        controller: 'EmployeeLoanAttachmentDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    fileName: null,
                                    file: null,
                                    fileContentType: null,
                                    fileContentName: null,
                                    fileContentType: null,
                                    remarks: null,
                                    status: null,
                                    createBy: null,
                                    createDate: null,
                                    updateBy: null,
                                    updateDate: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('employeeLoanAttachment', null, { reload: true });
                    }, function() {
                        $state.go('employeeLoanAttachment');
                    })
                }]
            })
            .state('employeeLoanAttachment.edit', {
                parent: 'employeeLoanAttachment',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/employeeLoanAttachment/employeeLoanAttachment-dialog.html',
                        controller: 'EmployeeLoanAttachmentDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['EmployeeLoanAttachment', function(EmployeeLoanAttachment) {
                                return EmployeeLoanAttachment.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('employeeLoanAttachment', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
