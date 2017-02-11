'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('instEmployee', {
                parent: 'entity',
                url: '/instEmployees',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instEmployee.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instEmployee/instEmployees.html',
                        controller: 'InstEmployeeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instEmployee');
                        $translatePartialLoader.addPart('empType');
                        $translatePartialLoader.addPart('maritalStatus');
                        $translatePartialLoader.addPart('bloodGroup');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('instEmployee.detail', {
                parent: 'entity',
                url: '/instEmployee/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.instEmployee.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instEmployee/instEmployee-detail.html',
                        controller: 'InstEmployeeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instEmployee');
                        $translatePartialLoader.addPart('empType');
                        $translatePartialLoader.addPart('maritalStatus');
                        $translatePartialLoader.addPart('bloodGroup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstEmployee', function($stateParams, InstEmployee) {
                        return InstEmployee.get({id : $stateParams.id});
                    }]
                }
            })
            .state('instEmployee.new', {
                parent: 'instEmployee',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instEmployee/instEmployee-dialog.html',
                        controller: 'InstEmployeeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    email: null,
                                    contactNo: null,
                                    fatherName: null,
                                    motherName: null,
                                    dob: null,
                                    category: null,
                                    gender: null,
                                    maritalStatus: null,
                                    bloodGroup: null,
                                    tin: null,
                                    image: null,
                                    imageContentType: null,
                                    nationality: null,
                                    nid: null,
                                    nidImage: null,
                                    nidImageContentType: null,
                                    birthCertNo: null,
                                    birthCertImage: null,
                                    birthCertImageContentType: null,
                                    conPerName: null,
                                    conPerMobile: null,
                                    conPerAddress: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('instEmployee', null, { reload: true });
                    }, function() {
                        $state.go('instEmployee');
                    })
                }]
            })
            .state('instEmployee.edit', {
                parent: 'instEmployee',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instEmployee/instEmployee-dialog.html',
                        controller: 'InstEmployeeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstEmployee', function(InstEmployee) {
                                return InstEmployee.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instEmployee', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('instEmployee.delete', {
                parent: 'instEmployee',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instEmployee/instEmployee-delete-dialog.html',
                        controller: 'InstEmployeeDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['InstEmployee', function(InstEmployee) {
                                return InstEmployee.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instEmployee', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
