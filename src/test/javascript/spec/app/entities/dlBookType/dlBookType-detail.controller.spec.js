'use strict';

describe('DlBookType Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockDlBookType, MockDlBookCat;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockDlBookType = jasmine.createSpy('MockDlBookType');
        MockDlBookCat = jasmine.createSpy('MockDlBookCat');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'DlBookType': MockDlBookType,
            'DlBookCat': MockDlBookCat
        };
        createController = function() {
            $injector.get('$controller')("DlBookTypeDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:dlBookTypeUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
