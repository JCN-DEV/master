'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('relationship', {
                parent: 'entity',
                url: '/relationships',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.relationship.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/relationship/relationships.html',
                        controller: 'RelationshipController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('relationship');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('relationship.detail', {
                parent: 'entity',
                url: '/relationship/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.relationship.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/relationship/relationship-detail.html',
                        controller: 'RelationshipDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('relationship');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Relationship', function($stateParams, Relationship) {
                        return Relationship.get({id : $stateParams.id});
                    }]
                }
            })
            .state('relationship.new', {
                parent: 'relationship',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/relationship/relationship-dialog.html',
                        controller: 'RelationshipDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('relationship');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            name: null,
                            description: null,
                            activeStatus: true,
                            createDate: null,
                            createBy: null,
                            updateDate: null,
                            updateBy: null,
                            id: null
                        };
                    }
                }

            })
            .state('relationship.edit', {
                parent: 'relationship',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/relationship/relationship-dialog.html',
                        controller: 'RelationshipDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('relationship');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Relationship', function($stateParams, Relationship) {
                        return Relationship.get({id : $stateParams.id});
                    }]
                }

            });
    });
