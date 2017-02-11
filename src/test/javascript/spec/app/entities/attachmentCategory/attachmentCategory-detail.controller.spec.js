'use strict';

describe('AttachmentCategory Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockAttachmentCategory, MockModule;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockAttachmentCategory = jasmine.createSpy('MockAttachmentCategory');
        MockModule = jasmine.createSpy('MockModule');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'AttachmentCategory': MockAttachmentCategory,
            'Module': MockModule
        };
        createController = function() {
            $injector.get('$controller')("AttachmentCategoryDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:attachmentCategoryUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
