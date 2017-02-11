'use strict';

angular.module('stepApp')
    .factory('AttachmentSearch', function ($resource) {
        return $resource('api/_search/attachments/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    })
    .factory('AttachmentEmployee', function ($resource) {
        return $resource('api/attachments/employee/:id', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('AttachmentByEmployeeAndName', function ($resource) {
        return $resource('api/attachments/employee/:id/:applicationName', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
