'use strict';

angular.module('stepApp')
    .factory('AttachmentCategorySearch', function ($resource) {
        return $resource('api/_search/attachmentCategorys/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    })
    .factory('AttachmentCategoryModule', function ($resource) {
        return $resource('api/attachmentCategorys/module/:id', {}, {
            'query': { method: 'GET', isArray: true}
        });
    })
    .factory('AttachmentCategoryByApplicationName', function ($resource) {
        return $resource('api/attachmentCategorys/applicationName/:name', {}, {
            'get': { method: 'GET', isArray: true}
        });
    }).factory('AttachmentCatByAppNameAndDesignation', function ($resource) {
        return $resource('api/attachmentCategorys/applicationName/type/:name/:designationId', {}, {
            'get': { method: 'GET', isArray: true}
        });
    });
