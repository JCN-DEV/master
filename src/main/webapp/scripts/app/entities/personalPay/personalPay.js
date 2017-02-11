'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('personalPay', {
                parent: 'entity',
                url: '/personalPays',
                data: {
                    authorities: ['ROLE_MPOADMIN'],
                    pageTitle: 'stepApp.personalPay.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/personalPay/personalPays.html',
                        controller: 'PersonalPayController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('personalPay');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('personalPay.detail', {
                parent: 'entity',
                url: '/personalPay/{id}',
                data: {
                    authorities: ['ROLE_MPOADMIN'],
                    pageTitle: 'stepApp.personalPay.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/personalPay/personalPay-detail.html',
                        controller: 'PersonalPayDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('personalPay');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PersonalPay', function ($stateParams, PersonalPay) {
                        return PersonalPay.get({id: $stateParams.id});
                    }]
                }
            })
            .state('personalPay.new', {
                parent: 'personalPay',
                url: '/new',
                data: {
                    authorities: ['ROLE_MPOADMIN'],
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/personalPay/personalPay-dialog.html',
                        controller: 'PersonalPayDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('personalPay');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return [{
                            payScale: null,
                            amount: null
                        }];
                    }
                }
            })
            /*onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
             $modal.open({
             templateUrl: 'scripts/app/entities/personalPay/personalPay-dialog.html',
             controller: 'PersonalPayDialogController',
             size: 'lg',
             resolve: {
             entity: function () {
             return {
             effectiveDate: null,
             dateCreated: null,
             dateModified: null,
             amount: null,
             status: null,
             id: null
             };
             }
             }
             }).result.then(function(result) {
             $state.go('personalPay', null, { reload: true });
             }, function() {
             $state.go('personalPay');
             })
             }]
             })*/
            .state('personalPay.edit', {
                parent: 'personalPay',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_MPOADMIN'],
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/personalPay/personalPay-dialog.html',
                        controller: 'PersonalPayDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('personalPay');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PersonalPay', function ($stateParams, PersonalPay) {
                        return PersonalPay.get({id: $stateParams.id});
                    }]
                }
            })
            /*onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
             $modal.open({
             templateUrl: 'scripts/app/entities/personalPay/personalPay-dialog.html',
             controller: 'PersonalPayDialogController',
             size: 'lg',
             resolve: {
             entity: ['PersonalPay', function(PersonalPay) {
             return PersonalPay.get({id : $stateParams.id});
             }]
             }
             }).result.then(function(result) {
             $state.go('personalPay', null, { reload: true });
             }, function() {
             $state.go('^');
             })
             }]
             })*/
            .state('personalPay.delete', {
                parent: 'personalPay',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_MPOADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function ($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/personalPay/personalPay-delete-dialog.html',
                        controller: 'PersonalPayDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['PersonalPay', function (PersonalPay) {
                                return PersonalPay.get({id: $stateParams.id});
                            }]
                        }
                    }).result.then(function (result) {
                            $state.go('personalPay', null, {reload: true});
                        }, function () {
                            $state.go('^');
                        })
                }]
            });
    });
