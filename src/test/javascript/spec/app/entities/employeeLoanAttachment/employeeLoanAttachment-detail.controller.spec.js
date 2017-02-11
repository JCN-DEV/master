'use strict';

describe('EmployeeLoanAttachment Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockEmployeeLoanAttachment, MockHrEmployeeInfo, MockAttachmentCategory;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockEmployeeLoanAttachment = jasmine.createSpy('MockEmployeeLoanAttachment');
        MockHrEmployeeInfo = jasmine.createSpy('MockHrEmployeeInfo');
        MockAttachmentCategory = jasmine.createSpy('MockAttachmentCategory');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'EmployeeLoanAttachment': MockEmployeeLoanAttachment,
            'HrEmployeeInfo': MockHrEmployeeInfo,
            'AttachmentCategory': MockAttachmentCategory
        };
        createController = function() {
            $injector.get('$controller')("EmployeeLoanAttachmentDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:employeeLoanAttachmentUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
