'use strict';

describe('Attachment Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockAttachment, MockInstEmployee, MockAttachmentCategory;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockAttachment = jasmine.createSpy('MockAttachment');
        MockInstEmployee = jasmine.createSpy('MockInstEmployee');
        MockAttachmentCategory = jasmine.createSpy('MockAttachmentCategory');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Attachment': MockAttachment,
            'InstEmployee': MockInstEmployee,
            'AttachmentCategory': MockAttachmentCategory
        };
        createController = function() {
            $injector.get('$controller')("AttachmentDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:attachmentUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
